package com.SchoolManagementSystem.system.dto.academic.request;

public record UpdateTwoSemesterRequest(
        SemesterUpdateRequest first,
        SemesterUpdateRequest second
) {
}
