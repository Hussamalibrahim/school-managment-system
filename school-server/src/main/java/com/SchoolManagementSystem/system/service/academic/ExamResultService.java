package com.SchoolManagementSystem.system.service.academic;

import com.SchoolManagementSystem.system.dto.academic.ExamResultDto;
import com.SchoolManagementSystem.system.dto.academic.request.SaveExamResultsRequest;
import com.SchoolManagementSystem.system.security.UserPrincipal;

import java.util.List;

public interface ExamResultService {

    ExamResultDto getById(UserPrincipal user, Long id);

    List<ExamResultDto> getByExam(Long examId, UserPrincipal user);

    List<ExamResultDto> saveResults(SaveExamResultsRequest request, UserPrincipal user);

    void delete(Long id, UserPrincipal user);

    List<ExamResultDto> getAll();

    List<ExamResultDto> getByStudent(Long refId);

    List<ExamResultDto> getGuardianChildrenResults(Long refId);
}
