package com.SchoolManagementSystem.System.dto.student;

import com.SchoolManagementSystem.System.entity.enumeration.Gender;
import com.SchoolManagementSystem.System.entity.student.Student;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Student}
 */
public record StudentDto(Long id, String registrationNumber,
                         String firstName, String lastName,
                         LocalDateTime createdAt, LocalDateTime updatedAt,
                         LocalDateTime deletedAt, Gender gender,
                         LocalDate dateOfBirth, String address, String status,
                         LocalDate enrollmentDate, String phone,
                         String notes) implements Serializable {
}