package com.SchoolManagementSystem.System.service.student;

import com.SchoolManagementSystem.System.dto.student.AttendanceDto;
import com.SchoolManagementSystem.System.dto.student.request.AttendanceCreateRequest;
import com.SchoolManagementSystem.System.dto.student.request.AttendanceRequest;

import java.util.List;

public interface AttendanceService {

    AttendanceDto save(AttendanceCreateRequest request);

    AttendanceDto update(Long id, AttendanceCreateRequest request);

    void delete(Long id);

    AttendanceDto getById(Long id);

    List<AttendanceDto> getAll();

    // Secreter
    List<AttendanceDto> getStudentAttendance(Long studentId);

    // Student last 7 days
    List<AttendanceDto> getMyAttendance(Long studentId);

    void saveAttendance(AttendanceRequest request);
}