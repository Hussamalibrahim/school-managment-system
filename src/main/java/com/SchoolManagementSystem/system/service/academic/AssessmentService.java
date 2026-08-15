package com.SchoolManagementSystem.System.service.academic;

import com.SchoolManagementSystem.System.dto.academic.AssessmentDto;
import com.SchoolManagementSystem.System.dto.academic.request.AssessmentCreateRequest;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.CrudService;

import java.util.List;

public interface AssessmentService
        extends CrudService<AssessmentDto, Long> {

    List<AssessmentDto> getSubjectAssessments(Long subjectId);


    AssessmentDto save(UserPrincipal user, AssessmentCreateRequest request);


    AssessmentDto update(Long id, UserPrincipal user, AssessmentCreateRequest request);

    List<AssessmentDto> getTeacherAssessments(Long teacherId);

    List<AssessmentDto> getMyAssessments(UserPrincipal user);

    List<AssessmentDto> getClassScheduleAssessments(Long classScheduleId, Long semesterId);

    List<AssessmentDto> getSchoolClassAssessments(Long schoolClassId, Long semesterId);

    List<AssessmentDto> getTeacherSubjectAssessments(Long teacherId);

    List<AssessmentDto> getTeacherAssessments(UserPrincipal user);
}