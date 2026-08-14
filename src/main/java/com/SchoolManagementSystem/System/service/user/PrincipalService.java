package com.SchoolManagementSystem.System.service.user;

import com.SchoolManagementSystem.System.dto.request.CreateUserRequest;
import com.SchoolManagementSystem.System.dto.user.PrincipalDto;
import com.SchoolManagementSystem.System.entity.enumeration.UserType;
import com.SchoolManagementSystem.System.service.CrudService;

import com.SchoolManagementSystem.System.dto.school.DashboardStatsDto;

public interface PrincipalService  extends CrudService<PrincipalDto, Long> {
    void createStaff(CreateUserRequest request);
    DashboardStatsDto getDashboardStats();
}
