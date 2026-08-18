package com.SchoolManagementSystem.system.dto.school;

public record SchoolAdminStatisticsDto(
        Long schoolId,
        String schoolName,
        String schoolCode,
        Boolean enabled,
        long users,
        long students,
        long guardians,
        long teachers,
        long secretaries) {
}