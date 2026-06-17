package com.SchoolManagementSystem.System.dto.school;

import com.SchoolManagementSystem.System.entity.school.SchoolSettings;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO for {@link SchoolSettings}
 */
public record SchoolSettingsDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                                LocalTime schoolStartTime, Integer periodDurationMinutes, Integer breakDurationMinutes,
                                Integer maxAllowedAbsences) implements Serializable {
}