package com.SchoolManagementSystem.system.dto.academic.request;

import com.SchoolManagementSystem.system.entity.enumeration.ContinuousCategory;

import java.time.LocalDate;

public record AssessmentUpdateRequest(

        Long subjectId,

        String name,

        ContinuousCategory category,

        Double maxScore,

        Double weight,

        LocalDate assessmentDate
) {
}