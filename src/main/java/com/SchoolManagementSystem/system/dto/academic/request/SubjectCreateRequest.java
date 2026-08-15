package com.SchoolManagementSystem.System.dto.academic.request;

import com.SchoolManagementSystem.System.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.System.entity.enumeration.SemesterName;

import java.io.Serializable;

public record SubjectCreateRequest (String name, GradeLevel gradeLevel, SemesterName semesterName) implements Serializable {
}
