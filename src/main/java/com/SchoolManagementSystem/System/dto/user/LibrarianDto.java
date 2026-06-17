package com.SchoolManagementSystem.System.dto.user;

import com.SchoolManagementSystem.System.entity.user.Librarian;
import lombok.Value;

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
    String email,
    String address,
    String status,
    LocalDate hireDate,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
)implements Serializable {
}