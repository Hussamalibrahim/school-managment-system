package com.SchoolManagementSystem.System.dto.finance;

import com.SchoolManagementSystem.System.entity.finance.Discount;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Discount}
 */
public record DiscountDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                          String name, Double percentage, String reason) implements Serializable {
}