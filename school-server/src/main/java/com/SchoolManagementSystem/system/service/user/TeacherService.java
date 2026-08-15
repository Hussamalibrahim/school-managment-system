package com.SchoolManagementSystem.system.service.user;

import com.SchoolManagementSystem.system.dto.student.StudentDto;
import com.SchoolManagementSystem.system.dto.user.TeacherDto;
import com.SchoolManagementSystem.system.service.CrudService;

import java.util.List;

public interface TeacherService extends CrudService<TeacherDto, Long> {
    TeacherDto getById(Long id);

    List<StudentDto> getMyStudents(Long teacherId);
}
