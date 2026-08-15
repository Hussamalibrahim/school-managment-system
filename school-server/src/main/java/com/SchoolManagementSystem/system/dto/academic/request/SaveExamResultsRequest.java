package com.SchoolManagementSystem.system.dto.academic.request;

import java.util.List;

public record SaveExamResultsRequest(
        Long examId,
        List<StudentExamScoreRequest> results
) {}
