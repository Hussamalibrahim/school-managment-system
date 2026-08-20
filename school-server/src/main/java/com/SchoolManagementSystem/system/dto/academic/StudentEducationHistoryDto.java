package com.SchoolManagementSystem.system.dto.academic;

public record StudentEducationHistoryDto(
        Long recordId,
        Long academicYearId,
        String academicYearName,
        Long schoolClassId,
        String gradeLevel,
        String section,
        Double finalAverage,
        Integer absenceDays,
        Boolean passed,
        String notes
) {
}