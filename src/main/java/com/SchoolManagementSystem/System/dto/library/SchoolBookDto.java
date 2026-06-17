package com.SchoolManagementSystem.System.dto.library;

import com.SchoolManagementSystem.System.entity.library.SchoolBook;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link SchoolBook}
 */
public record SchoolBookDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                            String name) implements Serializable {
}