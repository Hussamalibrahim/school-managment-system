package com.SchoolManagementSystem.system.service.admin;

import com.SchoolManagementSystem.system.dto.admin.AdminStatisticsDto;
import com.SchoolManagementSystem.system.dto.school.SchoolAdminStatisticsDto;

import java.util.List;

public interface AdminStatisticsService {

    AdminStatisticsDto getStatistics();

    List<SchoolAdminStatisticsDto> getSchoolsStatistics();

    SchoolAdminStatisticsDto getSchoolStatistics(Long schoolId);
}