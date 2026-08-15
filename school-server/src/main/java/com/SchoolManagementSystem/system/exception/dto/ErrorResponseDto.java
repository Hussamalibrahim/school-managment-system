package com.SchoolManagementSystem.system.exception.dto;

import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ErrorResponseDto {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String code;

    private String message;

    private String path;

    private Map<String, String> fieldErrors;


    public static ErrorResponseDto create(
            HttpStatus status,
            ErrorCode errorCode,
            String path
    ) {

        ErrorResponseDto response = new ErrorResponseDto();
        response.timestamp = LocalDateTime.now();
        response.status = status.value();
        response.error = status.getReasonPhrase();
        response.code = errorCode.name();
        response.message = errorCode.toString();

        response.path = path;

        return response;
    }


    public static ErrorResponseDto create(
            HttpStatus status,
            String message,
            String path
    ) {

        ErrorResponseDto response = new ErrorResponseDto();

        response.timestamp = LocalDateTime.now();

        response.status = status.value();

        response.error = status.getReasonPhrase();

        response.message = message;

        response.path = path;

        return response;
    }
}