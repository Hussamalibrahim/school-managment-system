package com.schoolManagementSystem.gateway.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolManagementSystem.gateway.ErrorCode;
import com.schoolManagementSystem.gateway.exception.dto.GatewayErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@Order(-2)
@RequiredArgsConstructor
public class GatewayExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper objectMapper;
    private final MessageSource messageSource;

    @Override
    public Mono<Void> handle(
            ServerWebExchange exchange,
            Throwable ex) {

        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        GatewayErrorResponse errorResponse =
                new GatewayErrorResponse(
                        LocalDateTime.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        ErrorCode.GATEWAY_ERROR,
                        getMessage(ErrorCode.GATEWAY_ERROR),
                        exchange.getRequest().getPath().value()
                );

        exchange.getResponse()
                .setStatusCode(status);

        exchange.getResponse()
                .getHeaders()
                .setContentType(MediaType.APPLICATION_JSON);

        try {

            byte[] bytes =
                    objectMapper.writeValueAsBytes(errorResponse);

            return exchange.getResponse()
                    .writeWith(
                            Mono.just(
                                    exchange.getResponse()
                                            .bufferFactory()
                                            .wrap(bytes)
                            )
                    );

        } catch (JsonProcessingException e) {

            return Mono.error(e);
        }
    }
    private String getMessage(ErrorCode code) {

        return messageSource.getMessage(
                code.name(),
                null,
                LocaleContextHolder.getLocale()
        );
    }
}