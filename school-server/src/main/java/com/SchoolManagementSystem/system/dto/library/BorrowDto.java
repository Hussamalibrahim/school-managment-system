package com.SchoolManagementSystem.system.dto.library;

import com.SchoolManagementSystem.system.entity.library.Borrow;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Borrow}
 */
public record BorrowDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                        LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate) implements Serializable {
}