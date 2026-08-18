package com.SchoolManagementSystem.system.dto.finance.response;

import com.SchoolManagementSystem.system.entity.enumeration.FeeType;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FeeStructureDto(
        Long id,
        String feeName,
        Long semesterId,
        SemesterName semester,
        GradeLevel gradeLevel,
        FeeType type,
        BigDecimal amount,
        LocalDate dueDate,
        Boolean active
) {
}