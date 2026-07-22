package com.SchoolManagementSystem.System.dto.academic;

import com.SchoolManagementSystem.System.entity.academic.Semester;
import com.SchoolManagementSystem.System.entity.enumeration.SemesterName;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Semester}
 */
public record SemesterDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                          SemesterName semesterName, LocalDate startDate, LocalDate endDate) implements Serializable {

}