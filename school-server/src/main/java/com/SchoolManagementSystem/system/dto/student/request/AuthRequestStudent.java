package com.SchoolManagementSystem.system.dto.student.request;

import com.SchoolManagementSystem.system.entity.enumeration.Gender;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;

import java.time.LocalDate;

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
