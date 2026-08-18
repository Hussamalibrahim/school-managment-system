package com.SchoolManagementSystem.system.dto.academic.request;

import com.SchoolManagementSystem.system.dto.school.AcademicYearDto;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;

import java.time.LocalDate;

public record AcademicYearWithSemestersRequest (AcademicYearDto academicYearDto, SemesterUpdateRequest semesterUpdateRequest1, SemesterUpdateRequest semesterUpdateRequest2) {
}
