package dev.aquashdw.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

//@Component
public class PreLoggingFilter implements GlobalFilter {
    private static final Logger logger = LoggerFactory.getLogger(PreLoggingFilter.class);
    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain
    ) {
        logger.trace("Executed filter in PreLoggingFilter");
        ServerHttpRequest httpRequest = exchange.getRequest();
        httpRequest.mutate()
                .headers(httpHeaders -> {
                    httpHeaders.add(
                            "likelion-gateway-request-id",
                            UUID.randomUUID().toString()
                    );
                    httpHeaders.add(
                            "likelion-gateway-request-time",
                            String.valueOf(Instant.now().toEpochMilli())
                    );
                })
                .build();

        return chain.filter(exchange);
    }
}
