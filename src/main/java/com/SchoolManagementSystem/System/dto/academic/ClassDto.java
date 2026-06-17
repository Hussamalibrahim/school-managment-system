package com.SchoolManagementSystem.System.dto.academic;

import com.SchoolManagementSystem.System.entity.academic.Class;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Class}
 */
public record ClassDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                       Integer level, String section, String location, Integer capacity) implements Serializable {
}