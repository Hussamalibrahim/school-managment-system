package com.SchoolManagementSystem.system.dto.library;

import com.SchoolManagementSystem.system.entity.library.Library;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Library}
 */
public record LibraryDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) implements Serializable {
}