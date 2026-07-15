package com.SchoolManagementSystem.System.security.service;


import com.SchoolManagementSystem.System.security.dto.AuthUserDto;
import com.SchoolManagementSystem.System.security.dto.RegisterRequest;

public interface AuthUserService {
    AuthUserDto findByEmail(String username);

    void register(RegisterRequest request);
}
