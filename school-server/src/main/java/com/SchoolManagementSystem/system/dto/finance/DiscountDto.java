package com.SchoolManagementSystem.system.dto.finance;
import com.SchoolManagementSystem.system.entity.enumeration.DiscountType;

import java.math.BigDecimal;

public record DiscountDto(
        Long id,
        Long feeId,
        String name,
        DiscountType discountType,
        BigDecimal value,
        String reason
) {
}