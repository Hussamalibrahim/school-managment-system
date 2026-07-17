package com.SchoolManagementSystem.System.service.user;

import com.SchoolManagementSystem.System.dto.user.GuardianDto;
import com.SchoolManagementSystem.System.security.dto.AuthRequestGuardian;
import com.SchoolManagementSystem.System.service.CrudService;

public interface GuardianService extends CrudService<GuardianDto, Long> {
    void save(AuthRequestGuardian authRequestGuardian);
}
