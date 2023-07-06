package com.evi.teamfindergateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.cloud.gateway.filter.factory.DedupeResponseHeaderGatewayFilterFactory.Strategy.RETAIN_FIRST;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;

@Configuration
public class LoadBalancedRoutes {

    @Bean
    public RouteLocator customLoadBalancedRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/users", "/api/v1/users/**","/api/v1/reports/**","/api/v1/friends/**","/api/v1/admin/**")
                        .filters(f-> f
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, RETAIN_FIRST.name())
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_ORIGIN, RETAIN_FIRST.name()))
                        .uri("lb://core-service")
                )
                .route(r -> r.path("/api/v1/auth", "/api/v1/auth/**","/api/v1/platform/**")
                        .filters(f-> f
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, RETAIN_FIRST.name())
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_ORIGIN, RETAIN_FIRST.name()))
                        .uri("lb://auth-service")
                )
                .route(r -> r.path("/api/v1/notify/**", "/api/v1/notification/**")
                        .filters(f-> f
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, RETAIN_FIRST.name())
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_ORIGIN, RETAIN_FIRST.name()))
                        .uri("lb://notification-service")
                )
                .route(r -> r.path("/api/v1/groups", "/api/v1/groups/**",
                                "/api/v1/search","/api/v1/search/**",
                                "/api/v1/userGroups","/api/v1/userGroups/**")
                        .filters(f-> f
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, RETAIN_FIRST.name())
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_ORIGIN, RETAIN_FIRST.name()))
                        .uri("lb://group-management-service")
                )
                .route(r -> r.path("/api/v1/chat/**","/ws/**",
                                "//chat/**","/privateChat/**","/api/v1/chatLogs/**",
                                "/api/v1/users/chatLogs/*","/api/v1/deletedGroupsLogs/**","/api/v1/messageRead/**",
                                "/api/v1/unreadMessages","/topic/**","/app/**")
                        .filters(f-> f
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, RETAIN_FIRST.name())
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_ORIGIN, RETAIN_FIRST.name()))
                        .uri("lb://chat-service")
                )
                .build();
    }
}
