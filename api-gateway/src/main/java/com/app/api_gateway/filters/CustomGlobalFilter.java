package com.app.api_gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("Logging from global filter. User has called {} service",exchange.getRequest().getURI());

        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            log.info("Logging from global filter after response, getting status code: {}",exchange.getResponse().getStatusCode());
                }));
    }

    @Override
    public int getOrder() {
        return 5;
    }
}
