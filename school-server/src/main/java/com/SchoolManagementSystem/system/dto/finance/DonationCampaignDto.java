package com.SchoolManagementSystem.system.dto.finance;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DonationCampaignDto(
        Long id,
        String name,
        String description,
        BigDecimal targetAmount,
        BigDecimal raisedAmount,
        BigDecimal remainingAmount,
        LocalDate startDate,
        LocalDate endDate,
        Boolean active
) {
}