package com.SchoolManagementSystem.System.dto.academic;

public record ExamResultDto(
        Long id,
        Long examId,
        Long studentId,
        Double score) {}
