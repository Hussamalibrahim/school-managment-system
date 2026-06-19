package com.SchoolManagementSystem.System.service.user;

import com.SchoolManagementSystem.System.dto.academic.TeacherSubjectDto;
import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.dto.user.TeacherDto;
import com.SchoolManagementSystem.System.service.CrudService;

import java.util.List;

public interface TeacherService extends CrudService<TeacherDto, Long> {
    boolean existsByNationalId(String nationalId);
    TeacherDto getById(Long id);

    List<StudentDto> getMyStudents(Long teacherId);
}
