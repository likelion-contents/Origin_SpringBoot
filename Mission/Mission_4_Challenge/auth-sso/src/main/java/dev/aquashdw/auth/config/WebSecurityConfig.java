package dev.aquashdw.auth.config;

import dev.aquashdw.auth.filter.SsoCookieHandler;
import dev.aquashdw.auth.infra.CustomUserDetailsService;
import dev.aquashdw.auth.infra.NaverOAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final NaverOAuth2Service oAuth2UserService;
    private final SsoCookieHandler ssoCookieHandler;

    public WebSecurityConfig(
            @Autowired CustomUserDetailsService customUserDetailsService,
            @Autowired NaverOAuth2Service oAuth2UserService,
            @Autowired SsoCookieHandler ssoCookieHandler
    ){
        this.userDetailsService = customUserDetailsService;
        this.oAuth2UserService = oAuth2UserService;
        this.ssoCookieHandler = ssoCookieHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        "/home/**",
                        "/user/signup/**",
                        "/",
                        "/css/**",
                        "/images/**",
                        "/js/**"
                )
                .permitAll()
                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/home")
                .successHandler(ssoCookieHandler)
                .permitAll()
            .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/home")
                .deleteCookies("JSEESIONID")
                .invalidateHttpSession(true)
                .permitAll()
            .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(this.oAuth2UserService)
                .and()
                .defaultSuccessUrl("/home")
            .and()
                .oauth2Client()
        ;
    }
}
