package com.SchoolManagementSystem.system.dto.academic;

public record ExamResultDto(
        Long id,
        Long examId,
        Long studentId,
        Double score) {}
