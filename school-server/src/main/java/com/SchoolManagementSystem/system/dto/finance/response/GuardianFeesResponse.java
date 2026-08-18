package com.SchoolManagementSystem.system.dto.finance.response;

import com.SchoolManagementSystem.system.dto.finance.GuardianStudentFeesDto;

import java.math.BigDecimal;
import java.util.List;

public record GuardianFeesResponse(
        List<GuardianStudentFeesDto> students,
        BigDecimal totalRequired,
        BigDecimal totalPaid,
        BigDecimal totalRemaining
) {
}