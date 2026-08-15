package com.SchoolManagementSystem.system.dto.school;

import com.SchoolManagementSystem.system.entity.enumeration.EducationStage;
import com.SchoolManagementSystem.system.entity.enumeration.SchoolType;

import java.time.LocalDateTime;
import java.util.Set;

public record SchoolDto(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,

        String name,
        String address,
        String phone,
        String logoPath,

        Set<EducationStage> educationStages,
        SchoolType schoolType
) {
}