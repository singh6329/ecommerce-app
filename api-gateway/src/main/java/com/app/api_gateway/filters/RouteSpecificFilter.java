package com.app.api_gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class RouteSpecificFilter extends AbstractGatewayFilterFactory<RouteSpecificFilter.Config> {

    public RouteSpecificFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return new OrderedGatewayFilter((exchange, chain) -> {
            log.info("Route specific filter calling with route: {}",exchange.getRequest().getURI());
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                log.info("After sending response, status code: {}",exchange.getResponse().getStatusCode());
            }));
        },6);
    }


    public static class Config{
    }
}
