package com.SchoolManagementSystem.System.dto.file.request;

import com.SchoolManagementSystem.System.entity.enumeration.FileType;
import jakarta.validation.constraints.NotNull;

public record StudentFileUploadRequest(

        @NotNull
        Long studentId,

        @NotNull
        FileType fileType

) {
}