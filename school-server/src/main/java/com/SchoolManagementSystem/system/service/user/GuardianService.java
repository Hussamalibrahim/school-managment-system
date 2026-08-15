package com.SchoolManagementSystem.system.service.user;

import com.SchoolManagementSystem.system.dto.user.GuardianDto;
import com.SchoolManagementSystem.system.security.dto.AuthRequestGuardian;
import com.SchoolManagementSystem.system.service.CrudService;

public interface GuardianService extends CrudService<GuardianDto, Long> {
    void save(AuthRequestGuardian authRequestGuardian);
}
