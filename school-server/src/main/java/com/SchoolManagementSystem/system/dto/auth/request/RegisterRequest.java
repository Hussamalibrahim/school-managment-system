package com.SchoolManagementSystem.system.dto.auth.request;

public record RegisterRequest(
        String firstName,
        String lastName,
        String nationalId,
        String email,
        String password) {
}
