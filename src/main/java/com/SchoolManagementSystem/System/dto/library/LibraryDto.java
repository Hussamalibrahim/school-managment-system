package com.SchoolManagementSystem.System.dto.library;

import com.SchoolManagementSystem.System.entity.library.Library;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Library}
 */
public record LibraryDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                         String name) implements Serializable {
}