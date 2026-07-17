package com.SchoolManagementSystem.System.utils.file;

import com.SchoolManagementSystem.System.entity.enumeration.FileOwnerType;

public final class FileFolderUtil {

    private FileFolderUtil() {
    }

    public static String getFolder(FileOwnerType ownerType, Long ownerId) {

        return switch (ownerType) {

            case STUDENT ->
                    "students/" + ownerId;

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
                    "others/" + ownerId;
        };
    }

}