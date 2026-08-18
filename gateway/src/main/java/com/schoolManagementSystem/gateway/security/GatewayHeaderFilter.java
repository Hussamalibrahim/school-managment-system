package com.schoolManagementSystem.gateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GatewayHeaderFilter implements GlobalFilter {

    public GatewayHeaderFilter(){
        log.info("GatewayHeaderFilter CREATED");
    }
    @Value("${gateway.secret}")
    private String gatewayKey;


    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {
        log.info("GatewayHeaderFilter EXECUTED");

        log.info("========== GATEWAY REQUEST ==========");
        log.info("PATH: {}", exchange.getRequest().getURI());
        ServerWebExchange modified =
                exchange.mutate().request(request ->
                                request.headers(headers -> {
                                    headers.set("X-GATEWAY", gatewayKey);}))
                        .build();

        log.info("Added X-GATEWAY header with value: {}", gatewayKey);
        return chain.filter(modified);
    }
}