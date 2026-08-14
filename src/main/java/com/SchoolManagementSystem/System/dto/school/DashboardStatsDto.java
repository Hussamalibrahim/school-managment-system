package com.SchoolManagementSystem.System.dto.school;

import java.io.Serializable;

public record DashboardStatsDto(
        long totalStudents,
        long totalTeachers,
        long totalGuardians,
        long totalClasses,
        long totalSubjects,
        long totalAnnouncements,
        long totalWarnings,
        long presentAttendanceCount,
        long absentAttendanceCount,
        long lateAttendanceCount,
        double totalPaymentsAmount
) implements Serializable {
}
