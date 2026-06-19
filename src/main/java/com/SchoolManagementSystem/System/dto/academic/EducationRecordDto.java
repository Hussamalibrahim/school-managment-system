
package com.SchoolManagementSystem.System.dto.academic;

import java.time.LocalDateTime;

public record EducationRecordDto(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,

        Long studentId,
        Long academicYearId,

        Double finalAverage,
        Integer absenceDays,
        Boolean passed,
        String notes
) {
}