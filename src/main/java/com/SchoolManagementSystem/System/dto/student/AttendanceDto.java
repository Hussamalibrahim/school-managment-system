package com.SchoolManagementSystem.System.dto.student;

import com.SchoolManagementSystem.System.entity.student.Attendance;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Attendance}
 */
public record AttendanceDto (
    Long id,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt,
    LocalDate attendanceDate,
    String status
)implements Serializable {
}