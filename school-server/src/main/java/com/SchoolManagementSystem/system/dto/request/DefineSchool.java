package com.SchoolManagementSystem.system.dto.request;

import com.SchoolManagementSystem.system.entity.enumeration.EducationStage;
import com.SchoolManagementSystem.system.entity.enumeration.SchoolType;

import java.util.Set;

public record DefineSchool (String name, SchoolType schoolType, Set<EducationStage> educationStages) {
}
