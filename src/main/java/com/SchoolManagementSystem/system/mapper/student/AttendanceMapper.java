package com.SchoolManagementSystem.System.mapper.student;

import com.SchoolManagementSystem.System.dto.student.AttendanceDto;
import com.SchoolManagementSystem.System.entity.student.Attendance;
import com.SchoolManagementSystem.System.entity.student.Student;

public final class AttendanceMapper {

    private AttendanceMapper() {}

    public static AttendanceDto toDto(Attendance attendance) {
        if (attendance == null) return null;

        return new AttendanceDto(
                attendance.getId(),
                attendance.getCreatedAt(),
                attendance.getUpdatedAt(),
                attendance.getDeletedAt(),

                attendance.getStudent() != null ? attendance.getStudent().getId() : null,

                attendance.getAttendanceDate(),
                attendance.getAttendanceStatus()
        );
    }

    public static Attendance toEntity(AttendanceDto dto) {
        if (dto == null) return null;

        Attendance attendance = new Attendance();

        attendance.setId(dto.id());
        attendance.setCreatedAt(dto.createdAt());
        attendance.setUpdatedAt(dto.updatedAt());
        attendance.setDeletedAt(dto.deletedAt());

        if (dto.studentId() != null) {
            Student student = new Student();
            student.setId(dto.studentId());
            attendance.setStudent(student);
        }

        attendance.setAttendanceDate(dto.attendanceDate());
        attendance.setAttendanceStatus(dto.attendanceStatus());

        return attendance;
    }
}