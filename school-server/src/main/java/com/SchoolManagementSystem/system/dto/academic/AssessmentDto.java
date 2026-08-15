package com.SchoolManagementSystem.system.dto.academic;

import com.SchoolManagementSystem.system.entity.enumeration.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AssessmentDto(

        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,

        Long classScheduleId,
        Long semesterId,

        Long teacherId,
        String name,
        ContinuousCategory category,
        Double maxScore,
        Double weight,
        LocalDate assessmentDate
) {
}