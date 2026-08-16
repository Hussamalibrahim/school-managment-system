package com.schoolManagementSystem.gateway.exception.dto;

import com.schoolManagementSystem.gateway.ErrorCode;

import java.time.LocalDateTime;

public record GatewayErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        ErrorCode code,
        String message,
        String path
) {
}