package com.SchoolManagementSystem.system.dto.request;

import com.SchoolManagementSystem.system.entity.enumeration.FileOwnerType;
import com.SchoolManagementSystem.system.entity.enumeration.FileType;
import com.SchoolManagementSystem.system.entity.enumeration.UserType;

public record UploadFileRequest(

        FileType fileType,

        FileOwnerType ownerType,

        Long ownerId,

        UserType uploadedByType,

        Long uploadedById

) {
}