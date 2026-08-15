package com.SchoolManagementSystem.system.dto.user;

import com.SchoolManagementSystem.system.entity.user.Guardian;

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
    String address,
    String occupation,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
)implements Serializable {
}