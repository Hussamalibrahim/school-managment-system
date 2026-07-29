package com.SchoolManagementSystem.System.service.academic;

import com.SchoolManagementSystem.System.dto.academic.ExamDto;
import com.SchoolManagementSystem.System.dto.academic.request.ExamCreateRequest;
import com.SchoolManagementSystem.System.dto.academic.request.ExamUpdateRequest;
import com.SchoolManagementSystem.System.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExamService {
    ExamDto save(ExamCreateRequest request, UserPrincipal user);

    ExamDto update(Long id, ExamUpdateRequest request, UserPrincipal user);

    void delete(Long id, UserPrincipal user);

    ExamDto getById(Long id);

    List<ExamDto> getAll();

    List<ExamDto> getByClass(Long classId, SemesterName semesterName);

    List<ExamDto> getBySubject(Long subjectId, SemesterName semesterName);

    List<ExamDto> getTeacherExams(Long classId, UserPrincipal user);

    List<ExamDto> getMyClassExams(UserPrincipal user);
}
