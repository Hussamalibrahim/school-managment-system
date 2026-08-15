package com.SchoolManagementSystem.system.exception.business;

import com.SchoolManagementSystem.system.exception.base.BusinessException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BusinessException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode, HttpStatus.NOT_FOUND);
    }

}