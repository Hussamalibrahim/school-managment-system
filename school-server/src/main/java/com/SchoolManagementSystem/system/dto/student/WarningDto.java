package com.SchoolManagementSystem.system.dto.student;

import com.SchoolManagementSystem.system.entity.student.Warning;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Warning}
 */
public record WarningDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                         LocalDate warningDate, String reason) implements Serializable {
}