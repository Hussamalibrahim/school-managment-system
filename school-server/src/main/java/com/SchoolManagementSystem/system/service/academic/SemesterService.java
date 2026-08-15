package com.SchoolManagementSystem.system.service.academic;

import com.SchoolManagementSystem.system.dto.academic.SemesterDto;
import com.SchoolManagementSystem.system.entity.academic.Semester;
import com.SchoolManagementSystem.system.service.CrudService;

public interface SemesterService extends CrudService<SemesterDto, Long> {
    Semester getCurrentSemester();
}
