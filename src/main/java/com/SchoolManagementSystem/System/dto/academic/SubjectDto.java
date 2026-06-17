package com.SchoolManagementSystem.System.dto.academic;

import com.SchoolManagementSystem.System.entity.academic.Subject;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Subject}
 */
public record SubjectDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                         String name, String description) implements Serializable {
}