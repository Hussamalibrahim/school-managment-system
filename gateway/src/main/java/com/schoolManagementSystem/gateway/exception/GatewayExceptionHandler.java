package com.schoolManagementSystem.gateway.exception;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import java.time.LocalDateTime;
import java.util.Map;


@Component
@Order(-1)
@RequiredArgsConstructor
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;
    @Override
    public Mono<Void> handle(
            ServerWebExchange exchange,
            Throwable ex) {

        exchange.getResponse()
                .getHeaders()
                .setContentType(MediaType.APPLICATION_JSON);

        HttpStatus status;
        if(ex.getMessage() != null &&
                ex.getMessage().contains("503")) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
        }else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        exchange.getResponse().setStatusCode(status);

        Map<String,Object> body =
                Map.of("timestamp", LocalDateTime.now(),
                        "status", status.value(),
                        "error", status.getReasonPhrase(),
                        "message", getMessage(status),
                        "path", exchange.getRequest()
                                .getPath()
                                .value());

        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(body);
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }

        return exchange.getResponse()
                .writeWith(Mono.just(
                                exchange.getResponse()
                                        .bufferFactory()
                                        .wrap(bytes))
                );
    }

    private String getMessage(HttpStatus status){
        return switch (status){
            case SERVICE_UNAVAILABLE -> "Requested service is unavailable";
            case INTERNAL_SERVER_ERROR -> "Internal server error";
            default -> "Unexpected error";
        };
    }
}