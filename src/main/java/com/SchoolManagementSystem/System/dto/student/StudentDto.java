package com.SchoolManagementSystem.System.dto.student;

import com.SchoolManagementSystem.System.entity.enumeration.Gender;
import com.SchoolManagementSystem.System.entity.enumeration.GradeLevel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record StudentDto(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,

        Long schoolId,
        Long schoolClassId,

        String registrationNumber,
        String firstName,
        String lastName,

        Gender gender,
        GradeLevel gradeLevel,

        LocalDate dateOfBirth,
        String address,
        String status,
        LocalDate enrollmentDate,
        String phone,
        String notes
) {
}