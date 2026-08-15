package com.SchoolManagementSystem.System.dto.student.request;

import com.SchoolManagementSystem.System.entity.enumeration.AttendanceStatus;

public record StudentAttendanceRequest(
        Long studentId,
        AttendanceStatus attendanceStatus
) {
}