package com.SchoolManagementSystem.system.dto.finance.request;

import com.SchoolManagementSystem.system.entity.enumeration.DiscountType;

import java.math.BigDecimal;

public record DiscountRequest(
        String name,
        DiscountType discountType,
        BigDecimal value,
        String reason
) {
}