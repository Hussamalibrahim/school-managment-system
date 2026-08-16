package com.SchoolManagementSystem.system.security.service;


import com.SchoolManagementSystem.system.dto.auth.AuthUserDto;
import com.SchoolManagementSystem.system.dto.auth.request.RegisterRequest;
import com.SchoolManagementSystem.system.entity.enumeration.UserType;

public interface AuthUserService {
    AuthUserDto findByEmail(String username);

    void register(RegisterRequest request);

    AuthUserDto deactivateAccountByEmail(String email);

    AuthUserDto deactivateAccountByIdAndRole(Long ownerId, UserType userType);

    AuthUserDto activateAccountByIdAndRole(Long ownerId, UserType userType);

    AuthUserDto activateAccountByEmail(String email);

    AuthUserDto findByEmailAndSchool(String email, String schoolCode);
}
