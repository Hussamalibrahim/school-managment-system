package com.schoolManagementSystem.gateway.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JwtService jwtService;

    @Value("${gateway.secret}")
    private String gatewayKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String header =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION);
        log.info("GatewayHeaderFilter EXECUTED");

        log.info("========== GATEWAY REQUEST ==========");
        log.info("PATH: {}", exchange.getRequest().getURI());

        if (header == null || !header.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }
        String token = header.substring(7);
        try {
            Long userId = jwtService.getRefId(token);
            String role = jwtService.getRole(token);
            Long schoolId = jwtService.getSchoolId(token);

            ServerWebExchange modified = exchange.mutate()
                    .request(request ->
                            request.headers(headers -> {
                                headers.set("X-USER-ID", userId.toString());
                                headers.set("X-ROLE", role);
                                headers.set("X-SCHOOL-ID", schoolId.toString());
                                headers.add("X-GATEWAY", gatewayKey);
                            })
                    ).build();

            return chain.filter(modified);
        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}