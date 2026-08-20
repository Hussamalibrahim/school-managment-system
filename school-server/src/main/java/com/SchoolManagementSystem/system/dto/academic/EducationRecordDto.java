
package com.SchoolManagementSystem.system.dto.academic;

import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;

import java.time.LocalDateTime;

public record EducationRecordDto(
        Long id,
        Long studentId,
        Long academicYearId,
        Double finalAverage,
        GradeLevel gradeLevel,
        Integer absenceDays,
        Boolean passed,
        String notes
) {
}