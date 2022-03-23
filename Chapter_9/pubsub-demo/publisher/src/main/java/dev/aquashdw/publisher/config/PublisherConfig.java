package dev.aquashdw.publisher.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublisherConfig {
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("boot.fanout.exchange");
    }
}
