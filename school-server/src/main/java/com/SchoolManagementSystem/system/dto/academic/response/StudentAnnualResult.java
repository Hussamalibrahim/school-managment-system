package com.SchoolManagementSystem.system.dto.academic.response;

public record StudentAnnualResult(
        Double average,
        int failedSubjects
) {
}