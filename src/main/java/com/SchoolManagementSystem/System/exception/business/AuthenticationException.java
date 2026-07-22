package com.SchoolManagementSystem.System.exception.business;

import com.SchoolManagementSystem.System.exception.base.BusinessException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class AuthenticationException extends BusinessException {

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode, HttpStatus.UNAUTHORIZED);
    }
}
