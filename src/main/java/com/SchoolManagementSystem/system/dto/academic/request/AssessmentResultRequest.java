package com.SchoolManagementSystem.System.dto.academic.request;

import java.util.List;

public record AssessmentResultRequest(Long assessmentId, List<StudentAssessmentResultRequest> results) {
}
