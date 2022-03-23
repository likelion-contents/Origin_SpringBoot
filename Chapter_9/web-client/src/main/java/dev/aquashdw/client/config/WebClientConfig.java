package dev.aquashdw.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${ncp.api.access-key:stub-api-key}")
    private String accessKey;

    @Bean
    public WebClient defaultWebClient(){
        return WebClient.create();
    }

    @Bean
    public WebClient actuatorClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8081/actuator")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public WebClient ncpWebClient(){
        return WebClient.builder()
                .defaultHeader("x-ncp-iam-access-key", accessKey)
                .build();
    }

    @Bean
    public WebClient randomDataClient(ObjectMapper baseConfig){
        ObjectMapper newMapper = baseConfig.copy();
        newMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer ->
                        configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(newMapper)))
                .build();

        return WebClient.builder()
                .baseUrl("https://random-data-api.com")
                .exchangeStrategies(exchangeStrategies)
                .build();
    }
}
