package com.SchoolManagementSystem.System.dto.school.request;

import com.SchoolManagementSystem.System.entity.enumeration.EducationStage;
import com.SchoolManagementSystem.System.entity.enumeration.SchoolType;

import java.util.Set;

public record SchoolRegisterRequest(

        // Principal
        String firstName,
        String lastName,
        String email,
        String password,
        String nationalId,

        // School
        String schoolName,
        Set<EducationStage> educationStages,
        SchoolType schoolType

) {}