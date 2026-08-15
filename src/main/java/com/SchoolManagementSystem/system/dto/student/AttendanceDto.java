package com.SchoolManagementSystem.System.dto.student;

import com.SchoolManagementSystem.System.entity.enumeration.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AttendanceDto(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,

        Long studentId,

        LocalDate attendanceDate,
        AttendanceStatus attendanceStatus
) {
}