package com.SchoolManagementSystem.system.exception.business;

import com.SchoolManagementSystem.system.exception.base.BusinessException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class StorageException extends BusinessException {

    public StorageException(ErrorCode errorCode) {
        super(errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public StorageException(
            ErrorCode errorCode,
            Throwable cause
    ) {
        super(errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
        initCause(cause);
    }

}