package com.SchoolManagementSystem.system.dto.auth;

public record AdminLoginRequest(
        String email,
        String password) {
}