package com.SchoolManagementSystem.System.dto.library;

import com.SchoolManagementSystem.System.entity.library.LibraryBook;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link LibraryBook}
 */
public record LibraryBookDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                             String title, String author, String isbn, String category,
                             String description) implements Serializable {
}