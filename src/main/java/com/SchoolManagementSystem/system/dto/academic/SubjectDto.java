package com.SchoolManagementSystem.System.dto.academic;

import com.SchoolManagementSystem.System.entity.academic.Subject;
import com.SchoolManagementSystem.System.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.System.entity.enumeration.SemesterName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Subject}
 */
public record SubjectDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                         String name, GradeLevel gradeLevel, SemesterName semesterName) implements Serializable {
}