package com.SchoolManagementSystem.system.dto.user;

import com.SchoolManagementSystem.system.entity.user.Librarian;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Librarian}
 */

public record LibrarianDto (
    Long id,
    String nationalId,
    String firstName,
    String lastName,
    String phone,
    String address,
    LocalDate hireDate,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
)implements Serializable {
}