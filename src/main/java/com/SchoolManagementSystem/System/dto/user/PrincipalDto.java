package com.SchoolManagementSystem.System.dto.user;

import com.SchoolManagementSystem.System.entity.user.Principal;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Principal}
 */

public record PrincipalDto (
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
    LocalDateTime deletedAt)
implements Serializable {
}