package com.SchoolManagementSystem.system.dto.finance;

import com.SchoolManagementSystem.system.entity.enumeration.DonationStatus;
import com.SchoolManagementSystem.system.entity.enumeration.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DonationDto(
        Long id,
        Long campaignId,
        Long guardianId,
        String guardianName,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        DonationStatus status,
        LocalDateTime donationDate,
        String receiptNumber,
        String notes
) {
}