package com.app.api_gateway.filters;

import com.app.api_gateway.services.JwtService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final JwtService jwtService;

    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                if (!config.isEnabled)
                {
                    return chain.filter(exchange);
                }
                String authorizationToken = exchange.getRequest().getHeaders().getFirst("Authorization");
                if(authorizationToken == null) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
                String bearerToken = authorizationToken.split("Bearer ")[1];
                Long id = jwtService.verifyToken(bearerToken);
                exchange.getRequest().mutate()
                        .header("X-user-Id",id.toString())
                        .build();
                return chain.filter(exchange);
            }
        };
    }

    @Data
    public static class Config{
        Boolean isEnabled;
    }
}
