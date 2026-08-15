package com.SchoolManagementSystem.system.dto.academic;

import com.SchoolManagementSystem.system.entity.academic.Subject;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Subject}
 */
public record SubjectDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                         String name, GradeLevel gradeLevel, SemesterName semesterName) implements Serializable {
}