package com.SchoolManagementSystem.System.dto.academic;

import com.SchoolManagementSystem.System.entity.academic.Assessment;
import com.SchoolManagementSystem.System.entity.enumeration.AssessmentCategory;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Assessment}
 */
public record AssessmentDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                            String name, AssessmentCategory category, Double maxScore, Double weight,
                            LocalDate assessmentDate) implements Serializable {
}