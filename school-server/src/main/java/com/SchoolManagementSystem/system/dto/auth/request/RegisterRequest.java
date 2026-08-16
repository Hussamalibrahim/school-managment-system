package com.SchoolManagementSystem.system.dto.auth;

public record RegisterRequest(
        String firstName,
        String lastName,
        String nationalId,
        String email,
        String password) {
}
