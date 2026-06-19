package com.SchoolManagementSystem.System.mapper.academic;

import com.SchoolManagementSystem.System.dto.academic.AssessmentDto;
import com.SchoolManagementSystem.System.entity.academic.Assessment;

public final class AssessmentMapper {
     private AssessmentMapper() {
     }

     public static AssessmentDto toDto(Assessment assessment) {
          if (assessment == null) return null;

          return new AssessmentDto(
                  assessment.getId(),
                  assessment.getCreatedAt(),
                  assessment.getUpdatedAt(),
                  assessment.getDeletedAt(),
                  assessment.getName(),
                  assessment.getCategory(),
                  assessment.getMaxScore(),
                  assessment.getWeight(),
                  assessment.getAssessmentDate()
          );
     }

     public static Assessment toEntity(AssessmentDto dto) {
          if (dto == null) return null;

          Assessment assessment = new Assessment();
          assessment.setId(dto.id());
          assessment.setCreatedAt(dto.createdAt());
          assessment.setUpdatedAt(dto.updatedAt());
          assessment.setDeletedAt(dto.deletedAt());
          assessment.setName(dto.name());
          assessment.setCategory(dto.category());
          assessment.setMaxScore(dto.maxScore());
          assessment.setWeight(dto.weight());
          assessment.setAssessmentDate(dto.assessmentDate());

          return assessment;
     }
}