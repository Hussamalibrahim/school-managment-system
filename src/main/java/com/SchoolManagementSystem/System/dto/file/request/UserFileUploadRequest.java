package com.SchoolManagementSystem.System.dto.file.request;

import com.SchoolManagementSystem.System.entity.enumeration.FileType;
import com.SchoolManagementSystem.System.entity.enumeration.UserType;
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