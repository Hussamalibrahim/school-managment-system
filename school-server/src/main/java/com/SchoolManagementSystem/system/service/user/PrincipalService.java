package com.SchoolManagementSystem.system.service.user;

import com.SchoolManagementSystem.system.dto.request.CreateUserRequest;
import com.SchoolManagementSystem.system.dto.user.PrincipalDto;
import com.SchoolManagementSystem.system.service.CrudService;

public interface PrincipalService  extends CrudService<PrincipalDto, Long> {
    void createStaff(CreateUserRequest request);

}
