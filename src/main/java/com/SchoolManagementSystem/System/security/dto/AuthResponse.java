package com.SchoolManagementSystem.System.security.dto;

public record AuthResponse(
        String token,
        String role,
        Long refId
) {}
