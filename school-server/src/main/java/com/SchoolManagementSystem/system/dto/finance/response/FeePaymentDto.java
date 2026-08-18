package com.SchoolManagementSystem.system.dto.finance.response;

import com.SchoolManagementSystem.system.entity.enumeration.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FeePaymentDto(
        Long id,
        Long feeId,
        BigDecimal amount,
        LocalDateTime paymentDate,
        PaymentMethod paymentMethod,
        String receiptNumber,

        BigDecimal requiredAmount,
        BigDecimal paidAmount,
        BigDecimal remainingAmount
) {
}