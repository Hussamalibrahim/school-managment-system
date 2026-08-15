package com.SchoolManagementSystem.system.dto.school.request;

import com.SchoolManagementSystem.system.entity.enumeration.EducationStage;
import com.SchoolManagementSystem.system.entity.enumeration.SchoolType;

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