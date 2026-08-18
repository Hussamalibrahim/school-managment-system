package com.SchoolManagementSystem.system.dto.school;


public record UserStatistics(
        long total,
        long principals,
        long teachers,
        long students,
        long guardians,
        long secretaries) {
}