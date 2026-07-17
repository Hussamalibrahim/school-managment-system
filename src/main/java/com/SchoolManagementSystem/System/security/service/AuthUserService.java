package com.SchoolManagementSystem.System.security.service;


import com.SchoolManagementSystem.System.entity.enumeration.UserType;
import com.SchoolManagementSystem.System.security.dto.AuthUserDto;
import com.SchoolManagementSystem.System.security.dto.RegisterRequest;

public interface AuthUserService {
    AuthUserDto findByEmail(String username);

    void register(RegisterRequest request);

    AuthUserDto deactivateAccountByEmail(String email);

    AuthUserDto deactivateAccountByIdAndRole(Long ownerId, UserType userType);

    AuthUserDto activateAccountByIdAndRole(Long ownerId, UserType userType);

    AuthUserDto activateAccountByEmail(String email);
}
