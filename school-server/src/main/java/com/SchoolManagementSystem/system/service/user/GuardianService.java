package com.SchoolManagementSystem.system.service.user;

import com.SchoolManagementSystem.system.dto.student.StudentDto;
import com.SchoolManagementSystem.system.dto.user.GuardianDto;
import com.SchoolManagementSystem.system.dto.user.request.AuthRequestGuardian;
import com.SchoolManagementSystem.system.service.CrudService;

import java.util.List;

public interface GuardianService extends CrudService<GuardianDto, Long> {
    void save(AuthRequestGuardian authRequestGuardian);

    List<StudentDto> getStudentGuardian(Long refId);
}
