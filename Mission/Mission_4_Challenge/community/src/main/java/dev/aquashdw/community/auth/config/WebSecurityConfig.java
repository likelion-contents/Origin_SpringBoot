package dev.aquashdw.community.auth.config;

import dev.aquashdw.community.auth.LikelionSsoConsts;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


// new
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                    .antMatchers(
                            "/",
                            "/home/**",
                            "/css/**",
                            "/images/**",
                            "/js/**"
                    )
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                .logout()
                    .logoutUrl("/user/logout")
                    .logoutSuccessUrl("/home")
                    .deleteCookies(LikelionSsoConsts.LIKELION_LOGIN_COOKIE)
                    .invalidateHttpSession(true)
                    .permitAll();
    }

}
