package com.SchoolManagementSystem.system.dto.academic.response;

import java.util.List;

public record StudentAcademicStatisticsDto(
        Long studentId,
        String studentName,
        Long academicYearId,
        Double average,
        Long passedSubjects,
        Long failedSubjects,
        List<SubjectAcademicStatisticsDto> subjects
) {
}