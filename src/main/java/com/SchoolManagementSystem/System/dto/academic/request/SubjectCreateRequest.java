package com.SchoolManagementSystem.System.dto.academic.request;

import com.SchoolManagementSystem.System.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.System.entity.enumeration.Semester;

import java.io.Serializable;

public record SubjectCreateRequest (String name, GradeLevel gradeLevel, Semester semester) implements Serializable {
}
