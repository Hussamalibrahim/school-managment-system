package com.SchoolManagementSystem.system.dto.academic.request;

import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;

import java.time.LocalDate;

public record SemesterUpdateRequest (SemesterName semesterName, LocalDate startDate, LocalDate endDate){
}
