package com.SchoolManagementSystem.system.security.service;


import com.SchoolManagementSystem.system.entity.enumeration.UserType;
import com.SchoolManagementSystem.system.security.dto.AuthUserDto;
import com.SchoolManagementSystem.system.security.dto.RegisterRequest;

public interface AuthUserService {
    AuthUserDto findByEmail(String username);

    void register(RegisterRequest request);

    AuthUserDto deactivateAccountByEmail(String email);

    AuthUserDto deactivateAccountByIdAndRole(Long ownerId, UserType userType);

    AuthUserDto activateAccountByIdAndRole(Long ownerId, UserType userType);

    AuthUserDto activateAccountByEmail(String email);

    AuthUserDto findByEmailAndSchool(String email, String schoolCode);
}
