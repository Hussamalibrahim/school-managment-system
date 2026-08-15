package com.SchoolManagementSystem.system.dto.school;

import com.SchoolManagementSystem.system.entity.school.AcademicYear;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link AcademicYear}
 */
public record AcademicYearDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                              String name, LocalDate startDate, LocalDate endDate,
                              Boolean currentYear) implements Serializable {
}