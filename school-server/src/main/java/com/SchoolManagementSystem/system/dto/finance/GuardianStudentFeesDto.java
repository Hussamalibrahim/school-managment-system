package com.SchoolManagementSystem.system.dto.finance;

import java.math.BigDecimal;
import java.util.List;

public record GuardianStudentFeesDto(
        Long studentId,
        String studentName,
        List<StudentFeeDto> fees,
        BigDecimal totalRequired,
        BigDecimal totalPaid,
        BigDecimal totalRemaining
) {
}
