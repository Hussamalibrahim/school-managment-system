package com.SchoolManagementSystem.system.dto.academic.request;

import com.SchoolManagementSystem.system.entity.enumeration.ContinuousCategory;

import java.time.LocalDate;

public record AssessmentRequest(
        Long classScheduleId,
        Long semesterId,
        Long teacherId,
        String name,
        ContinuousCategory category,
        LocalDate assessmentDate
) {
}