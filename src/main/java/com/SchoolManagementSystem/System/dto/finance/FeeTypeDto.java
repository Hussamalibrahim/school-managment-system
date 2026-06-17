package com.SchoolManagementSystem.System.dto.finance;

import com.SchoolManagementSystem.System.entity.finance.FeeType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link FeeType}
 */
public record FeeTypeDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                         String name) implements Serializable {
}