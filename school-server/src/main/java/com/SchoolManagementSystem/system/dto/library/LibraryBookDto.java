package com.SchoolManagementSystem.system.dto.library;

import com.SchoolManagementSystem.system.entity.library.LibraryBook;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link LibraryBook}
 */
public record LibraryBookDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                             String title, String author, String isbn, String category,
                             String description) implements Serializable {
}