package com.SchoolManagementSystem.System.dto.academic.request;

import com.SchoolManagementSystem.System.entity.enumeration.ContinuousCategory;

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