package com.SchoolManagementSystem.system.security.dto;

import com.SchoolManagementSystem.system.entity.enumeration.Role;

public record AuthUserDto(
        String email,
        String password,
        Role role,
        Long refId,
        Long schoolId,
        String schoolCode
) {}
