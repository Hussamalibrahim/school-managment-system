package com.SchoolManagementSystem.System.exception;

import com.SchoolManagementSystem.System.exception.base.BusinessException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;

public class AlreadyExistsException extends BusinessException {

    public AlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}