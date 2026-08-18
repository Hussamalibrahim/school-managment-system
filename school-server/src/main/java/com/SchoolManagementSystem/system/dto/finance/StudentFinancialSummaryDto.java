package com.SchoolManagementSystem.system.dto.finance;

import java.math.BigDecimal;

public record StudentFinancialSummaryDto(
        Long studentId,
        String studentName,
        BigDecimal totalDue,
        BigDecimal totalPaid,
        BigDecimal totalRemaining
) {
}