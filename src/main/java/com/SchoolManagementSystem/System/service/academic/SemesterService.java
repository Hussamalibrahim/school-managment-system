package com.SchoolManagementSystem.System.service.academic;

import com.SchoolManagementSystem.System.dto.academic.SemesterDto;
import com.SchoolManagementSystem.System.entity.academic.Semester;
import com.SchoolManagementSystem.System.service.CrudService;

public interface SemesterService extends CrudService<SemesterDto, Long> {
    Semester getCurrentSemester();
}
