package com.SchoolManagementSystem.system.dto.file.request;

import com.SchoolManagementSystem.system.entity.enumeration.FileType;
import jakarta.validation.constraints.NotNull;

public record StudentFileUploadRequest(

        @NotNull
        Long studentId,

        @NotNull
        FileType fileType

) {
}