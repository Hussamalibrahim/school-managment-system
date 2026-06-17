package com.SchoolManagementSystem.System.dto.student;

import com.SchoolManagementSystem.System.entity.student.StudentGuardian;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link StudentGuardian}
 */
public record StudentGuardianDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                                 Boolean primaryGuardian) implements Serializable {
}