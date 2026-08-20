package com.SchoolManagementSystem.system.dto.academic.response;

public record SubjectStatisticsDto(
        Long subjectId,
        String subjectName,
        Double average,
        Long passedStudents,
        Long failedStudents,
        Double successRate
) {
}