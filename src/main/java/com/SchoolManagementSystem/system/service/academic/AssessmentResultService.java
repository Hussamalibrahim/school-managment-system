package com.SchoolManagementSystem.System.service.academic;

import com.SchoolManagementSystem.System.dto.academic.AssessmentResultDto;
import com.SchoolManagementSystem.System.dto.academic.request.AssessmentResultRequest;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.CrudService;

import java.util.List;

public interface AssessmentResultService {

    void saveResults(UserPrincipal user, AssessmentResultRequest request);

    void updateResults(UserPrincipal user, AssessmentResultRequest request);

    AssessmentResultDto getById(Long id);

    List<AssessmentResultDto> getAssessmentResults(Long assessmentId);

    List<AssessmentResultDto> getStudentResults(Long studentId);

    void delete(Long id);
}