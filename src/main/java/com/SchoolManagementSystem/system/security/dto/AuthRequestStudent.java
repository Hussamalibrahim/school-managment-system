package com.SchoolManagementSystem.System.security.dto;

import com.SchoolManagementSystem.System.entity.enumeration.Gender;
import com.SchoolManagementSystem.System.entity.enumeration.GradeLevel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AuthRequestStudent (
        String registrationNumber,
        String firstName,
        String lastName,
        String phone,
        String email,
        Gender gender,
        GradeLevel gradeLevel,

        LocalDate dateOfBirth,
        String address,
        String status,
        LocalDate enrollmentDate,
        String notes){
}
