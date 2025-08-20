package com.dtcenter.graphql_master;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class LoggingConfig implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long init = System.currentTimeMillis();

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    long end = System.currentTimeMillis();

                    ServerHttpResponse response = exchange.getResponse();
                    System.out.println(exchange.getRequest().getURI() + " => " + response.getStatusCode() + " (" + (end - init) + "ms)");
                }));
    }
}
