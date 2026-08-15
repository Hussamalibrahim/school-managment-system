package com.SchoolManagementSystem.System.utils.file;

import com.SchoolManagementSystem.System.entity.enumeration.FileOwnerType;
import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;

public final class FileFolderUtil {

    private FileFolderUtil() {
    }

    public static String getFolder(FileOwnerType ownerType, Long ownerId) {

        return switch (ownerType) {

            case STUDENT ->
                    "results/" + ownerId;

            case TEACHER ->
                    "teachers/" + ownerId;

            case GUARDIAN ->
                    "guardians/" + ownerId;

            case SCHOOL ->
                    "schools/" + ownerId;

            case LIBRARY ->
                    "libraries/" + ownerId;

            case ANNOUNCEMENT ->
                    "announcements/" + ownerId;

            default ->
                    throw new ValidationException(
                            ErrorCode.INVALID_OWNER_TYPE
                    );
        };
    }

}