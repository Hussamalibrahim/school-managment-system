package com.SchoolManagementSystem.System.dto.school;

import com.SchoolManagementSystem.System.entity.school.School;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link School}
 */
public record SchoolDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, String name,
                        String schoolType, String address, String phone, String logoPath, Boolean supportsPrimary,
                        Boolean supportsMiddle, Boolean supportsSecondary) implements Serializable {
}