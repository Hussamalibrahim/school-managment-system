package com.SchoolManagementSystem.System.dto.library;

import com.SchoolManagementSystem.System.entity.library.StudentBook;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link StudentBook}
 */
public record StudentBookDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                             LocalDate givenDate) implements Serializable {
}