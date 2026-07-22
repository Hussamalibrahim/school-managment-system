package com.SchoolManagementSystem.System.exception.business;

import com.SchoolManagementSystem.System.exception.base.BusinessException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class BusinessRuleException extends BusinessException {

    public BusinessRuleException(ErrorCode errorCode) {
        super(errorCode, HttpStatus.BAD_REQUEST);
    }

}