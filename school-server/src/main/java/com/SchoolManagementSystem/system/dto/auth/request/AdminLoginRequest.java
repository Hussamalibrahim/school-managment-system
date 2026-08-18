package com.SchoolManagementSystem.system.dto.auth.request;

public record AdminLoginRequest(
        String email,
        String password) {
}