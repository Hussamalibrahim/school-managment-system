package com.SchoolManagementSystem.system.dto.finance.request;

import com.SchoolManagementSystem.system.entity.enumeration.FeeType;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FeeStructureRequest(
        String feeName,
        Long semesterId,
        GradeLevel gradeLevel,
        FeeType feeType,
        BigDecimal amount,
        LocalDate dueDate
) {
}