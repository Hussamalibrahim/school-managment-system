package com.SchoolManagementSystem.System.service.user;

import com.SchoolManagementSystem.System.dto.academic.TeacherSubjectDto;
import com.SchoolManagementSystem.System.dto.user.TeacherDto;
import com.SchoolManagementSystem.System.service.CrudService;

public interface TeacherService extends CrudService<TeacherDto, Long> {
    boolean existsByNationalId(String nationalId);

}
