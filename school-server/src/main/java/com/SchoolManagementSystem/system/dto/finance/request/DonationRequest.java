package com.SchoolManagementSystem.system.dto.finance.request;

import com.SchoolManagementSystem.system.entity.enumeration.PaymentMethod;

import java.math.BigDecimal;

public record DonationRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String notes
) {
}
