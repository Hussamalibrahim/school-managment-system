package com.SchoolManagementSystem.system.dto.academic.request;

import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;

import java.io.Serializable;

public record SubjectCreateRequest (String name, GradeLevel gradeLevel, SemesterName semesterName) implements Serializable {
}
