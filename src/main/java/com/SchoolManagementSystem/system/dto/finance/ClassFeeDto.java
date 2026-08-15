package com.SchoolManagementSystem.System.dto.finance;

import com.SchoolManagementSystem.System.entity.finance.ClassFee;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link ClassFee}
 */
public record ClassFeeDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                          Double amount) implements Serializable {
}