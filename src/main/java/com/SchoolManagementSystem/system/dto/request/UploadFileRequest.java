package com.SchoolManagementSystem.System.dto.request;

import com.SchoolManagementSystem.System.entity.enumeration.FileOwnerType;
import com.SchoolManagementSystem.System.entity.enumeration.FileType;
import com.SchoolManagementSystem.System.entity.enumeration.UserType;

public record UploadFileRequest(

        FileType fileType,

        FileOwnerType ownerType,

        Long ownerId,

        UserType uploadedByType,

        Long uploadedById

) {
}