package com.SchoolManagementSystem.System.dto.user;

import com.SchoolManagementSystem.System.entity.user.Guardian;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Guardian}
 */
public record GuardianDto (
    Long id,
    String nationalId,
    String firstName,
    String lastName,
    String phone,
    String email,
    String address,
    String occupation,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
)implements Serializable {
}