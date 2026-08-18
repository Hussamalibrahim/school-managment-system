package com.SchoolManagementSystem.system.dto.finance;

import java.math.BigDecimal;
import java.time.LocalDate;

public record StudentFeeDto(
        Long feeId,
        BigDecimal amount,
        BigDecimal discount,
        BigDecimal requiredAmount,
        BigDecimal paidAmount,
        BigDecimal remainingAmount,
        LocalDate dueDate
) {
}
