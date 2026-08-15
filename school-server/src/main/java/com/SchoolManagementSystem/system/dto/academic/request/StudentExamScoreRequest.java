package com.SchoolManagementSystem.system.dto.academic.request;

public record StudentExamScoreRequest(
        Long studentId,
        Double score
) {}