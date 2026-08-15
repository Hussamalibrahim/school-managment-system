package com.SchoolManagementSystem.system.dto.student.request;

import com.SchoolManagementSystem.system.entity.enumeration.AttendanceStatus;

import java.time.LocalDate;

public record AttendanceCreateRequest (Long studentId , LocalDate attendanceDate, AttendanceStatus attendanceStatus){
}
