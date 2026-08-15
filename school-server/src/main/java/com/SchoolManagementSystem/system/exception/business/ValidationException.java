package com.SchoolManagementSystem.system.exception.business;

import com.SchoolManagementSystem.system.exception.base.BusinessException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class ValidationException extends BusinessException {

    public ValidationException(ErrorCode errorCode) {
        super(errorCode, HttpStatus.BAD_REQUEST);
    }

}