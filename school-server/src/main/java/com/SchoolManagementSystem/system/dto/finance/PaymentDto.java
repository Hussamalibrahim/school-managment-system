package com.SchoolManagementSystem.system.dto.finance;

import com.SchoolManagementSystem.system.entity.finance.Payment;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Payment}
 */
public record PaymentDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                         Double amount, LocalDate paymentDate, String notes) implements Serializable {
}