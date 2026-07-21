package com.SchoolManagementSystem.System.exception;

import com.SchoolManagementSystem.System.exception.base.BusinessException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;

public class StorageException extends BusinessException {

    public StorageException(ErrorCode errorCode) {
        super(errorCode);
    }
}