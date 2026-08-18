package com.SchoolManagementSystem.system.service.academic;

import com.SchoolManagementSystem.system.dto.academic.SemesterDto;
import com.SchoolManagementSystem.system.dto.academic.request.SemesterUpdateRequest;
import com.SchoolManagementSystem.system.dto.academic.request.UpdateTwoSemesterRequest;
import com.SchoolManagementSystem.system.entity.academic.Semester;
import com.SchoolManagementSystem.system.service.CrudService;

public interface SemesterService extends CrudService<SemesterDto, Long> {
    SemesterDto getCurrentSemester();

    void updateSemester(Long academicYearId, SemesterUpdateRequest semesterUpdateRequest);

    void updateTwoSemester(Long academicYearId, UpdateTwoSemesterRequest updateTwoSemesterRequest);
}
