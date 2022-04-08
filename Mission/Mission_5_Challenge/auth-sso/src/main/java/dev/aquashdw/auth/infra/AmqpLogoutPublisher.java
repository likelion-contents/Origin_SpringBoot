package dev.aquashdw.auth.infra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AmqpLogoutPublisher implements LogoutPublisherService {
    private static final Logger logger = LoggerFactory.getLogger(AmqpLogoutPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange fanoutExchange;

    public AmqpLogoutPublisher(
            RabbitTemplate rabbitTemplate,
            FanoutExchange fanoutExchange
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.fanoutExchange = fanoutExchange;
    }

    @Override
    public void publishLogoutEvent(String cookieValue) {
        logger.debug("publishing logout event of: {}", cookieValue);
        rabbitTemplate.convertAndSend(
                fanoutExchange.getName(),
                "",
                cookieValue
        );
    }
}
