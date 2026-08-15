package com.SchoolManagementSystem.system.dto.academic;

import com.SchoolManagementSystem.system.entity.academic.Semester;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Semester}
 */
public record SemesterDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                          SemesterName semesterName, LocalDate startDate, LocalDate endDate) implements Serializable {

}