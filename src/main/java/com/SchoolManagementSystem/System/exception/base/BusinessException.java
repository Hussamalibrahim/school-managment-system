package com.SchoolManagementSystem.System.exception;

import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    protected BusinessException(ErrorCode errorCode) {
        super(errorCode.name());
        this.errorCode = errorCode;
    }
}