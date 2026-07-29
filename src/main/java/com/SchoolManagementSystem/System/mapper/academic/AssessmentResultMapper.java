package com.SchoolManagementSystem.System.mapper.academic;

import com.SchoolManagementSystem.System.dto.academic.AssessmentResultDto;
import com.SchoolManagementSystem.System.entity.academic.AssessmentResult;

public final class AssessmentResultMapper {
    private AssessmentResultMapper() {
    }

    public static AssessmentResultDto toDto(AssessmentResult result) {
        if (result == null) return null;

        return new AssessmentResultDto(
                result.getId(),
                result.getCreatedAt(),
                result.getUpdatedAt(),
                result.getDeletedAt(),

                result.getStudent() == null ? null : result.getStudent().getId(),
                result.getAssessment() == null ? null : result.getAssessment().getId(),

                result.getScore()
        );
    }

    public static AssessmentResult toEntity(AssessmentResultDto dto) {
        if (dto == null) return null;

        AssessmentResult result = new AssessmentResult();
        result.setId(dto.id());
        result.setCreatedAt(dto.createdAt());
        result.setUpdatedAt(dto.updatedAt());
        result.setDeletedAt(dto.deletedAt());
        result.setScore(dto.score());

        return result;
    }

    public static void updateEntity(AssessmentResult result, AssessmentResultDto dto) {
        result.setScore(dto.score());
    }
}