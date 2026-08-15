package com.SchoolManagementSystem.system.dto.student.request;

import com.SchoolManagementSystem.system.entity.enumeration.AttendanceStatus;

public record StudentAttendanceRequest(
        Long studentId,
        AttendanceStatus attendanceStatus
) {
}