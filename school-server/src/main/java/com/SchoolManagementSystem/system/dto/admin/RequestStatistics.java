package com.SchoolManagementSystem.system.dto.admin;

public record RequestStatistics(
        long pending,
        long approved,
        long rejected
) {
}