package com.SchoolManagementSystem.System.dto.student.request;

import com.SchoolManagementSystem.System.entity.enumeration.AttendanceStatus;

import java.time.LocalDate;

public record AttendanceCreateRequest (Long studentId , LocalDate attendanceDate, AttendanceStatus attendanceStatus){
}
