package dev.aquashdw.community.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient authSsoWebClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8000/check-user")
                .build();
    }
}
