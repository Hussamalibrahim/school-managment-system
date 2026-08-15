package com.SchoolManagementSystem.System.dto.request;

import com.SchoolManagementSystem.System.entity.enumeration.EducationStage;
import com.SchoolManagementSystem.System.entity.enumeration.SchoolType;

import java.util.Set;

public record DefineSchool (String name, SchoolType schoolType, Set<EducationStage> educationStages) {
}
