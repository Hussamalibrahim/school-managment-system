package com.SchoolManagementSystem.system.dto.finance.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DonationCampaignRequest(
        String name,
        String description,
        BigDecimal targetAmount,
        LocalDate startDate,
        LocalDate endDate
) {
}