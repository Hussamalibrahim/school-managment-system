package com.SchoolManagementSystem.system.dto.finance;

import com.SchoolManagementSystem.system.entity.finance.FeeType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link FeeType}
 */
public record FeeTypeDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                         String name) implements Serializable {
}