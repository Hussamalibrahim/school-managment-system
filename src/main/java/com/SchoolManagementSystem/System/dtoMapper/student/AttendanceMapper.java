package com.SchoolManagementSystem.System.dtoMapper.student;

import com.SchoolManagementSystem.System.dto.student.AttendanceDto;
import com.SchoolManagementSystem.System.entity.student.Attendance;

public final class AttendanceMapper {

    private AttendanceMapper() {}

    public static AttendanceDto toDto(Attendance attendance) {
        if (attendance == null) return null;

        return new AttendanceDto(
                attendance.getId(),
                attendance.getCreatedAt(),
                attendance.getUpdatedAt(),
                attendance.getDeletedAt(),
                attendance.getAttendanceDate(),
                attendance.getStatus()
        );
    }

    public static Attendance toEntity(AttendanceDto dto) {
        if (dto == null) return null;

        Attendance attendance = new Attendance();

        attendance.setId(dto.id());
        attendance.setCreatedAt(dto.createdAt());
        attendance.setUpdatedAt(dto.updatedAt());
        attendance.setDeletedAt(dto.deletedAt());
        attendance.setAttendanceDate(dto.attendanceDate());
        attendance.setStatus(dto.status());

        return attendance;
    }
}
