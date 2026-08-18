package com.SchoolManagementSystem.system.dto.school;

public record SchoolStatisticsDto(
        long users,
        long students,
        long guardians,
        long teachers,
        long secretaries) {
}