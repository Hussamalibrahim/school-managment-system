package com.SchoolManagementSystem.System.service.student;

import com.SchoolManagementSystem.System.dto.student.WarningDto;
import com.SchoolManagementSystem.System.service.CrudService;

import java.util.List;

public interface WarningService extends CrudService<WarningDto, Long> {
    List<WarningDto> getWarningsByStudentId(Long studentId);
}
