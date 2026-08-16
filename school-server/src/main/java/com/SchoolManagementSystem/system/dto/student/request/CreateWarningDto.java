package com.SchoolManagementSystem.system.dto.student.request;

import com.SchoolManagementSystem.system.entity.enumeration.WarningReason;
import jakarta.validation.constraints.NotNull;

public record CreateWarningDto(

        @NotNull
        Long studentId,

        @NotNull
        WarningReason reason,

        String message
) {
}