package com.SchoolManagementSystem.system.service.internal;

import com.SchoolManagementSystem.system.dto.internal.InternalStudentDto;

public interface InternalStudentService {

    InternalStudentDto getStudent(Long studentId);

    boolean exists(Long studentId);
}