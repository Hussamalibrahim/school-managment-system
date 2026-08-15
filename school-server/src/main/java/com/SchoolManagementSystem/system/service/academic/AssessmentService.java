package com.SchoolManagementSystem.system.service.academic;

import com.SchoolManagementSystem.system.dto.academic.AssessmentDto;
import com.SchoolManagementSystem.system.dto.academic.request.AssessmentCreateRequest;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.CrudService;

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