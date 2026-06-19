package com.SchoolManagementSystem.System.service.user;

import com.SchoolManagementSystem.System.dto.request.CreateUserRequest;
import com.SchoolManagementSystem.System.dto.user.PrincipalDto;
import com.SchoolManagementSystem.System.service.CrudService;

public interface PrincipalService  extends CrudService<PrincipalDto, Long> {
    void createStaff(CreateUserRequest request);
}
