package dev.aquashdw.subscriber.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriberConfig {
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("boot.fanout.exchange");
    }

    @Bean
    public Queue autoGenQueue(){
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding(
            Queue queue,
            FanoutExchange fanoutExchange
    ){
        return BindingBuilder
                .bind(queue)
                .to(fanoutExchange);
    }
}
