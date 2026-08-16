package com.schoolManagementSystem.gateway.security;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolManagementSystem.gateway.ErrorCode;
import com.schoolManagementSystem.gateway.exception.ErrorResponse;
import com.schoolManagementSystem.gateway.exception.dto.GatewayErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final MessageSource messageSource;

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
            log.warn("Invalid JWT token for request: {}", exchange.getRequest().getPath());
            return writeErrorResponse(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    ErrorCode.INVALID_TOKEN);
        }
    }

    private Mono<Void> writeErrorResponse(
            ServerWebExchange exchange,
            HttpStatus status,
            ErrorCode code) {

        ErrorResponse response =
                buildResponse(
                        status,
                        code,
                        exchange);


        exchange.getResponse()
                .setStatusCode(status);

        exchange.getResponse()
                .getHeaders()
                .setContentType(
                        MediaType.APPLICATION_JSON
                );
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(response);
            return exchange.getResponse()
                    .writeWith(
                            Mono.just(
                                    exchange.getResponse()
                                            .bufferFactory()
                                            .wrap(bytes)));
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize gateway error response", e);
            return Mono.error(e);
        }
    }


    private ErrorResponse buildResponse(
            HttpStatus status,
            ErrorCode code,
            ServerWebExchange exchange) {

        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                code.name(),
                getMessage(code),
                exchange.getRequest()
                        .getPath()
                        .value()
        );
    }


    private String getMessage(ErrorCode code) {

        return messageSource.getMessage(
                code.name(),
                null,
                LocaleContextHolder.getLocale()
        );
    }
}