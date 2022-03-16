package dev.aquashdw.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(authorizeRequests -> {
                    authorizeRequests.antMatchers("/user/logout").permitAll();
                    authorizeRequests.anyRequest().authenticated();
                })
                .oauth2Login(oauth2Login -> {
                    oauth2Login.loginPage("/oauth2/authorization/likelion-oidc");
                })
                .oauth2Client(Customizer.withDefaults())
                .logout(logout -> {
                    logout.logoutUrl("/user/logout");
                    logout.logoutSuccessUrl("/");
                    logout.invalidateHttpSession(true);
                })
        ;
        return http.build();
    }
}
