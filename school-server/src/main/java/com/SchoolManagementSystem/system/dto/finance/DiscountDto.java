package com.SchoolManagementSystem.system.dto.finance;

import com.SchoolManagementSystem.system.entity.finance.Discount;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Discount}
 */
public record DiscountDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                          String name, Double percentage, String reason) implements Serializable {
}