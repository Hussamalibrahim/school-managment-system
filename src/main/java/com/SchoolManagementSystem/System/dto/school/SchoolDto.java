package com.SchoolManagementSystem.System.dto.school;

import com.SchoolManagementSystem.System.entity.enumeration.EducationStage;
import com.SchoolManagementSystem.System.entity.enumeration.SchoolCategory;

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

        Set<EducationStage> supportedStages,
        SchoolCategory schoolCategory
) {
}