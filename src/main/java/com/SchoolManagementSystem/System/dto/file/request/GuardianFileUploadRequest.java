package com.SchoolManagementSystem.System.dto.file.request;

import com.SchoolManagementSystem.System.entity.enumeration.FileType;
import jakarta.validation.constraints.NotNull;

public record GuardianFileUploadRequest(

        @NotNull
        Long guardianId,

        @NotNull
        FileType fileType

) {
}