package com.SchoolManagementSystem.system.dto.academic;

public record SemesterResultDto(
        Long id,
        Long studentId,
        Long semesterId,
        Long subjectId,
        String subjectName,
        Double continuousAverage,
        Double examScore,
        Double finalScore
) {
}