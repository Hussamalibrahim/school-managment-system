package com.SchoolManagementSystem.system.service.student;

import com.SchoolManagementSystem.system.dto.student.respones.GuardianStudentWarningsDto;
import com.SchoolManagementSystem.system.dto.student.WarningDto;
import com.SchoolManagementSystem.system.dto.student.request.CreateWarningDto;
import com.SchoolManagementSystem.system.dto.student.respones.WarningStatisticsDto;
import com.SchoolManagementSystem.system.entity.enumeration.WarningReason;
import com.SchoolManagementSystem.system.service.CrudService;

import java.util.List;

public interface WarningService extends CrudService<WarningDto, Long> {
    WarningDto create(CreateWarningDto dto, Long refId);

    List<GuardianStudentWarningsDto> getGuardianChildrenWarnings(Long guardianId);

    List<WarningStatisticsDto> getWarningStatistics(WarningReason reason, Long count);

}
