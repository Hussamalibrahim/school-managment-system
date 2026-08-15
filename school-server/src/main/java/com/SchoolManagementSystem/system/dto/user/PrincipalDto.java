package com.SchoolManagementSystem.system.dto.user;

import com.SchoolManagementSystem.system.entity.user.Principal;

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
    String address,
    LocalDate hireDate,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt)
implements Serializable {
}