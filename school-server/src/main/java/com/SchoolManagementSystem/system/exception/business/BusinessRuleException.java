package com.SchoolManagementSystem.system.exception.business;

import com.SchoolManagementSystem.system.exception.base.BusinessException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class BusinessRuleException extends BusinessException {

    public BusinessRuleException(ErrorCode errorCode) {
        super(errorCode, HttpStatus.BAD_REQUEST);
    }

}