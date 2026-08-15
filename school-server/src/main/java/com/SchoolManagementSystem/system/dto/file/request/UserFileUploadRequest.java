package com.SchoolManagementSystem.system.dto.file.request;

import com.SchoolManagementSystem.system.entity.enumeration.FileType;
import com.SchoolManagementSystem.system.entity.enumeration.UserType;
import jakarta.validation.constraints.NotNull;

public record UserFileUploadRequest(

        @NotNull
        Long employeeId,

        @NotNull
        UserType employeeType,

        @NotNull
        FileType fileType

) {
}