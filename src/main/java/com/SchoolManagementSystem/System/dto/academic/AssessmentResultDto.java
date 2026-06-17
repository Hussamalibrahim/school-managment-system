package com.SchoolManagementSystem.System.dto.academic;

import com.SchoolManagementSystem.System.entity.academic.AssessmentResult;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link AssessmentResult}
 */
public record AssessmentResultDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                                  Double score, String notes) implements Serializable {
}