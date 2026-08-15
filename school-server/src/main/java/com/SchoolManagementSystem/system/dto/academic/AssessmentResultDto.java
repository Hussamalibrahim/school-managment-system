package com.SchoolManagementSystem.system.dto.academic;

import com.SchoolManagementSystem.system.entity.academic.AssessmentResult;

import java.time.LocalDateTime;

/**
 * DTO for {@link AssessmentResult}
 */
public record AssessmentResultDto(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,

        Long studentId,
        Long assessmentId,

        Double score) {}