package com.SchoolManagementSystem.system.service.school;

import com.SchoolManagementSystem.system.dto.school.SchoolAdminStatisticsDto;
import com.SchoolManagementSystem.system.dto.school.SchoolStatisticsDto;

import java.util.List;

public interface StatisticsService {
    SchoolStatisticsDto getSchoolsStatistics();
}
