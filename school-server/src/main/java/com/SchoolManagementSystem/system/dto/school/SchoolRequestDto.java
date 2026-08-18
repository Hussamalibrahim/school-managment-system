package com.SchoolManagementSystem.system.dto.school;

import com.SchoolManagementSystem.system.entity.enumeration.EducationStage;
import com.SchoolManagementSystem.system.entity.enumeration.SchoolRequestStatus;
import com.SchoolManagementSystem.system.entity.enumeration.SchoolType;

import java.time.LocalDateTime;
import java.util.Set;

public record SchoolRequestDto(
        Long id,
        Long schoolId,
        String schoolName,
        String schoolCode,
        SchoolType schoolType,
        Set<EducationStage> educationStages,
        SchoolRequestStatus status,
        String rejectionReason,
        LocalDateTime reviewedAt) {
}