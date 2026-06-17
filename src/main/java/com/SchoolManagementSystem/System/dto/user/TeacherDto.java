package com.SchoolManagementSystem.System.dto.user;

import com.SchoolManagementSystem.System.entity.user.Teacher;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Teacher}
 */
public record TeacherDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                         String nationalId, String firstName, String lastName, String phone, String email,
                         String address, String status, LocalDate hireDate,
                         String specialization) implements Serializable {
}