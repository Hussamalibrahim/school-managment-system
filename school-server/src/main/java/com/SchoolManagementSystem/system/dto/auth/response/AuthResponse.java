package com.SchoolManagementSystem.system.dto.auth.response;

public record AuthResponse(
        String token,
        String email,
        Long schoolId,
        Long refId
) {}
