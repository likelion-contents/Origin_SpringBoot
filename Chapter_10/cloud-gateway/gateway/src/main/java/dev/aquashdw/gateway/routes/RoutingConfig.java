package dev.aquashdw.gateway.routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RoutingConfig {
//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route("community-shop", predicate -> predicate
                        .path("/api/shop/**")
                        .filters(filter -> filter
                                .rewritePath(
                                        "/api/(?<path>.*)",
                                        "/${path}"
                                ))
                        .uri("http://localhost:8081"))
                .build();
    }
}
