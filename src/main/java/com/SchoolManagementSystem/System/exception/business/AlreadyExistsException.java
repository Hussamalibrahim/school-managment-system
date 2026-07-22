package com.SchoolManagementSystem.System.exception.business;

import com.SchoolManagementSystem.System.exception.base.BusinessException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends BusinessException {

    public AlreadyExistsException(ErrorCode errorCode) {
        super(errorCode, HttpStatus.CONFLICT);
    }

}