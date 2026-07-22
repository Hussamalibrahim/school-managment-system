package com.SchoolManagementSystem.System.security.dto;

public record RegisterRequest(
        String firstName,
        String lastName,
        String nationalId,
        String email,
        String password) {
}
