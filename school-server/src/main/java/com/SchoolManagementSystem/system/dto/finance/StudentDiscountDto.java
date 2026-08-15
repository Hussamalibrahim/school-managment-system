package com.SchoolManagementSystem.system.dto.finance;

import com.SchoolManagementSystem.system.entity.finance.StudentDiscount;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link StudentDiscount}
 */
public record StudentDiscountDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                                 LocalDateTime deletedAt) implements Serializable {
}