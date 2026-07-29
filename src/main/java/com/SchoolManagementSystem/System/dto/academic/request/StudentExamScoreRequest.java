package com.SchoolManagementSystem.System.dto.academic.request;

public record StudentExamScoreRequest(
        Long studentId,
        Double score
) {}