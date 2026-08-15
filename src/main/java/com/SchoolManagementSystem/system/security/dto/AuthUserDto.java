package com.SchoolManagementSystem.System.security.dto;

import com.SchoolManagementSystem.System.entity.enumeration.Role;

public record AuthUserDto(
        String email,
        String password,
        Role role,
        Long refId,
        Long schoolId,
        String schoolCode
) {}
