package dev.aquashdw.community.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RabbitListener(queues = "#{autoGenQueue.name}")
public class AmqpLogoutSubscriber implements LogoutCacheService {
    private static final Logger logger = LoggerFactory.getLogger(AmqpLogoutSubscriber.class);

    private final List<String> loggedOutUsers = new ArrayList<>();

    @Override
    public boolean checkUserStatus(String cookieValue) {
        if (loggedOutUsers.contains(cookieValue)){
            logger.debug("user already logged out: {}", cookieValue);
            loggedOutUsers.remove(cookieValue);
            return false;
        }
        return true;
    }

    @RabbitHandler
    public void receiveMessage(String cookieValue){
        logger.debug("received logged out user info: {}", cookieValue);
        loggedOutUsers.add(cookieValue);
    }
}
