package com.SchoolManagementSystem.system.dto.academic.response;

public record StudentYearStatisticsDto(
        Long studentId,
        String studentName,
        Long academicYearId,
        Double average,
        Integer passedSubjects,
        Integer failedSubjects
) {
}