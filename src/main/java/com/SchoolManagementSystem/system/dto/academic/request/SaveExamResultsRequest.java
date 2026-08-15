package com.SchoolManagementSystem.System.dto.academic.request;

import java.util.List;

public record SaveExamResultsRequest(
        Long examId,
        List<StudentExamScoreRequest> results
) {}
