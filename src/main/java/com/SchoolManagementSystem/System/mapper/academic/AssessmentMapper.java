package com.SchoolManagementSystem.System.mapper.academic;

import com.SchoolManagementSystem.System.dto.academic.AssessmentDto;
import com.SchoolManagementSystem.System.dto.academic.request.AssessmentCreateRequest;
import com.SchoolManagementSystem.System.entity.academic.Assessment;
import com.SchoolManagementSystem.System.entity.user.Teacher;

public final class AssessmentMapper {

     private AssessmentMapper() {
     }

     public static AssessmentDto toDto(Assessment assessment) {

          if (assessment == null)
               return null;

          return new AssessmentDto(

                  assessment.getId(),
                  assessment.getCreatedAt(),
                  assessment.getUpdatedAt(),
                  assessment.getDeletedAt(),

                  assessment.getClassSchedule() == null ? null : assessment.getClassSchedule().getId(),
                  assessment.getSemester() == null ? null : assessment.getSemester().getId(),
                  assessment.getTeacher() == null ? null : assessment.getTeacher().getId(),

                  assessment.getName(),
                  assessment.getCategory(),
                  assessment.getMaxScore(),
                  assessment.getWeight(),
                  assessment.getAssessmentDate()
          );
     }

     public static Assessment toEntity(AssessmentDto dto) {

          if (dto == null)
               return null;

          Assessment assessment = new Assessment();

          assessment.setId(dto.id());
          assessment.setCreatedAt(dto.createdAt());
          assessment.setUpdatedAt(dto.updatedAt());
          assessment.setDeletedAt(dto.deletedAt());

          if (dto.teacherId() != null) {
               Teacher teacher = new Teacher();
               teacher.setId(dto.teacherId());

               assessment.setTeacher(teacher);
          }
          assessment.setName(dto.name());
          assessment.setCategory(dto.category());
          assessment.setMaxScore(dto.maxScore());
          assessment.setWeight(dto.weight());
          assessment.setAssessmentDate(dto.assessmentDate());

          return assessment;
     }

     public static void updateEntity(
             Assessment assessment,
             AssessmentDto dto) {

          if (dto.teacherId() != null) {
               Teacher teacher = new Teacher();
               teacher.setId(dto.teacherId());

               assessment.setTeacher(teacher);
          }

          assessment.setName(dto.name());
          assessment.setCategory(dto.category());
          assessment.setMaxScore(dto.maxScore());
          assessment.setWeight(dto.weight());
          assessment.setAssessmentDate(dto.assessmentDate());
     }

     public static void fromCreateRequest(
             Assessment assessment,
             AssessmentCreateRequest request) {

          assessment.setName(request.name());
          assessment.setCategory(request.category());
          assessment.setMaxScore(request.maxScore());
          assessment.setWeight(request.weight());
          assessment.setAssessmentDate(request.assessmentDate());
     }
}