package com.SchoolManagementSystem.system.security.dto;

public record AuthResponse(
        String token,
        String role,
        Long schoolId,
        Long refId
) {}
