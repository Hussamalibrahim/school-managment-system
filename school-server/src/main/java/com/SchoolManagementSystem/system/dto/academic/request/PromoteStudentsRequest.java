package com.SchoolManagementSystem.system.dto.academic.request;

public record PromoteStudentsRequest(
        Long fromClassId,
        Long targetClassId
) {
}
