package com.SchoolManagementSystem.system.service.student;

import com.SchoolManagementSystem.system.config.AttendanceStatistics;
import com.SchoolManagementSystem.system.dto.student.AttendanceDto;
import com.SchoolManagementSystem.system.dto.student.StudentDto;
import com.SchoolManagementSystem.system.dto.student.request.AttendanceCreateRequest;
import com.SchoolManagementSystem.system.dto.student.request.AttendanceRequest;
import com.SchoolManagementSystem.system.entity.enumeration.AttendanceStatus;

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

    List<AttendanceDto> getGuardianStudentsAttendance(Long guardianId);

    List<AttendanceDto> getGuardianStudentAttendance(Long guardianId, Long studentId);

    AttendanceStatistics getAttendanceStatistics(Long studentId);

    List<StudentDto> getStudentsExceededAttendanceStatus(AttendanceStatus status, long limit);
}