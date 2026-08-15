package com.SchoolManagementSystem.system.dto.academic;

import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;

import java.time.LocalDateTime;

public record SchoolClassDto(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,

        Long schoolId,

        GradeLevel gradeLevel,
        String section,
        String location,
        Integer capacity
) {
}