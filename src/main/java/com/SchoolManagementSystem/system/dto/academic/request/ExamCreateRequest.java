package com.SchoolManagementSystem.System.dto.academic.request;

import com.SchoolManagementSystem.System.entity.enumeration.ExamCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ExamCreateRequest(
        @NotNull
        Long schoolClassId,
        @NotNull
        Long subjectId,
        @NotNull
        ExamCategory category,
        @NotNull
        @Positive
        Double maxScore,
        @NotNull
        @Positive
        Double weight,
        @NotNull
        LocalDateTime examDateTime,
        @NotNull
        @Positive
        Integer durationMinutes) {
}