package com.SchoolManagementSystem.system.dto.school;

public record SchoolStatistics(
        long total,
        long enabled,
        long disabled
) {
}