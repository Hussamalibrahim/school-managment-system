package com.SchoolManagementSystem.system.dto.student.request;

import java.time.LocalDate;
import java.util.List;

public record AttendanceRequest(
        LocalDate attendanceDate,
        List<StudentAttendanceRequest> students
) {
}