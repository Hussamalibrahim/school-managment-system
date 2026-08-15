package com.SchoolManagementSystem.system.dto.academic.request;

import java.util.List;

public record AssessmentResultRequest(Long assessmentId, List<StudentAssessmentResultRequest> results) {
}
