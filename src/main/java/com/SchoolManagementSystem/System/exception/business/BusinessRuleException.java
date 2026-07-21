package com.SchoolManagementSystem.System.exception;

import com.SchoolManagementSystem.System.exception.base.BusinessException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;

public class BusinessRuleException extends BusinessException {

    public BusinessRuleException(ErrorCode errorCode) {
        super(errorCode);
    }
}