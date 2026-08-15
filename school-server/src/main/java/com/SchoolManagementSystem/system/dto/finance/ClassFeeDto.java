package com.SchoolManagementSystem.system.dto.finance;

import com.SchoolManagementSystem.system.entity.finance.ClassFee;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link ClassFee}
 */
public record ClassFeeDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                          Double amount) implements Serializable {
}