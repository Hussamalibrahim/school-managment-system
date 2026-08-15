package com.SchoolManagementSystem.System.service.academic;

import com.SchoolManagementSystem.System.dto.academic.ExamResultDto;
import com.SchoolManagementSystem.System.dto.academic.request.SaveExamResultsRequest;
import com.SchoolManagementSystem.System.security.UserPrincipal;

import java.util.List;

public interface ExamResultService {

    ExamResultDto getById(UserPrincipal user, Long id);

    List<ExamResultDto> getByExam(Long examId, UserPrincipal user);

    List<ExamResultDto> saveResults(SaveExamResultsRequest request, UserPrincipal user);

    void delete(Long id, UserPrincipal user);

    List<ExamResultDto> getAll();

    List<ExamResultDto> getStudentResults(Long studentId);
}
