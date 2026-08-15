package com.SchoolManagementSystem.System.exception.base;

import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final HttpStatus status;

    protected BusinessException(
            ErrorCode errorCode,
            HttpStatus status
    ) {
        super(errorCode.name());
        this.errorCode = errorCode;
        this.status = status;
    }
}