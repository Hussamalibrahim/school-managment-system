package com.SchoolManagementSystem.system.dto.academic.response;

public record AcademicYearStatisticsDto(
        Long academicYearId,
        String academicYearName,
        Long totalStudents,
        Long passedStudents,
        Long failedStudents,
        Double average,
        Double successRate,
        Double failureRate
) {
}