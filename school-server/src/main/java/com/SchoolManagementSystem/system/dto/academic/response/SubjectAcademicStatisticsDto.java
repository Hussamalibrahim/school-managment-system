package com.SchoolManagementSystem.system.dto.academic.response;

public record SubjectAcademicStatisticsDto(
        Long subjectId,
        String subjectName,
        Double average,
        Boolean passed
) {
}