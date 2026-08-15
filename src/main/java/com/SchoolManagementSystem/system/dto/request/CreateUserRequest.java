package com.SchoolManagementSystem.System.dto.request;

import com.SchoolManagementSystem.System.entity.enumeration.Role;

import java.time.LocalDate;

public record CreateUserRequest(

        Role role,

        String nationalId,

        String firstName,

        String lastName,

        String password,

        String phone,

        String email,

        String address,

        LocalDate hireDate,

        String specialization
) {
}