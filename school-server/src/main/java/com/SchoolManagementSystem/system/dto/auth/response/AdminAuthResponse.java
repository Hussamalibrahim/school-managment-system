package com.SchoolManagementSystem.system.dto.auth.response;

public record AdminAuthResponse(
        String token,
        String role) {
}
