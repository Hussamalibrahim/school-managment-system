package com.SchoolManagementSystem.System.exception.business;

import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import lombok.Getter;

@Getter
public class JwtAuthenticationException extends RuntimeException {

    private final ErrorCode errorCode;

    public JwtAuthenticationException(ErrorCode errorCode) {
        super(errorCode.name());
        this.errorCode = errorCode;
    }
}