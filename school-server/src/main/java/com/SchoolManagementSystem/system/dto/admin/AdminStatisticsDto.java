package com.SchoolManagementSystem.system.dto.admin;

import com.SchoolManagementSystem.system.dto.school.SchoolStatistics;
import com.SchoolManagementSystem.system.dto.school.UserStatistics;

public record AdminStatisticsDto(
        SchoolStatistics schools,
        RequestStatistics requests,
        UserStatistics users
) {
}