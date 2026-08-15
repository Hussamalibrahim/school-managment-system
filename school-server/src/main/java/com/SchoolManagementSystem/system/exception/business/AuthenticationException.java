package com.SchoolManagementSystem.system.exception.business;

import com.SchoolManagementSystem.system.exception.base.BusinessException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class AuthenticationException extends BusinessException {

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode, HttpStatus.UNAUTHORIZED);
    }
}
