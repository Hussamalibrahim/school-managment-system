package com.SchoolManagementSystem.system.dto.file.request;

import com.SchoolManagementSystem.system.entity.enumeration.FileType;
import jakarta.validation.constraints.NotNull;

public record GuardianFileUploadRequest(

        @NotNull
        Long guardianId,

        @NotNull
        FileType fileType

) {
}