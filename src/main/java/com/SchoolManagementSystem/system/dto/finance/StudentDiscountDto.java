package com.SchoolManagementSystem.System.dto.finance;

import com.SchoolManagementSystem.System.entity.finance.StudentDiscount;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link StudentDiscount}
 */
public record StudentDiscountDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                                 LocalDateTime deletedAt) implements Serializable {
}