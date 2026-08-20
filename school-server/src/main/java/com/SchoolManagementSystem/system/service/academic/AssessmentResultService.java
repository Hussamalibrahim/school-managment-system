package com.SchoolManagementSystem.system.service.academic;

import com.SchoolManagementSystem.system.dto.academic.AssessmentResultDto;
import com.SchoolManagementSystem.system.dto.academic.request.AssessmentResultRequest;
import com.SchoolManagementSystem.system.security.UserPrincipal;

import java.util.List;

public interface AssessmentResultService {

    void saveResults(UserPrincipal user, AssessmentResultRequest request);

    void updateResults(UserPrincipal user, AssessmentResultRequest request);

    AssessmentResultDto getById(Long id);

    List<AssessmentResultDto> getAssessmentResults(Long assessmentId);

    List<AssessmentResultDto> getStudentResults(Long studentId);

    void delete(Long id);

    List<AssessmentResultDto> getGuardianChildrenResults(Long refId);
}