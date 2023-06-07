package com.evi.teamfindergateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadBalancedRoutes {

    @Bean
    public RouteLocator customLoadBalancedRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/user", "/api/v1/user/*")
                        .uri("lb://core-service")
                )
                .route(r -> r.path("/api/v1/auth", "/api/v1/auth/*")
                        .uri("lb://auth-service")
                )
                .build();
    }
}
