package com.SchoolManagementSystem.system.dto.student;

import com.SchoolManagementSystem.system.entity.enumeration.WarningReason;
import com.SchoolManagementSystem.system.entity.student.Warning;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Warning}
 */
public record WarningDto(Long id,
                         Long studentId,
                         WarningReason reason,
                         String message,
                         LocalDate warningDate,
                         LocalDateTime createdAt,
                         LocalDateTime updatedAt,
                         LocalDateTime deletedAt) implements Serializable {
}