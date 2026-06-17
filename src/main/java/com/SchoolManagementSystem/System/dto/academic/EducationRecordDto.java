package com.SchoolManagementSystem.System.dto.academic;

import com.SchoolManagementSystem.System.entity.academic.EducationRecord;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link EducationRecord}
 */
public record EducationRecordDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                                 String schoolName, String overallResult, String notes) implements Serializable {
}