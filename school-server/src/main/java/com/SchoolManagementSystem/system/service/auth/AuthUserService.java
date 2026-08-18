package com.SchoolManagementSystem.system.service.auth;


import com.SchoolManagementSystem.system.dto.auth.AuthUserDto;
import com.SchoolManagementSystem.system.dto.auth.request.AuthRequest;
import com.SchoolManagementSystem.system.dto.auth.request.RegisterRequest;
import com.SchoolManagementSystem.system.dto.auth.response.AuthResponse;
import com.SchoolManagementSystem.system.entity.enumeration.UserType;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthUserService {
    AuthUserDto findByEmail(String username);

    AuthResponse login(String schoolCode, AuthRequest request);

    void register(RegisterRequest request);

    AuthUserDto deactivateAccountByEmail(String email);

    AuthUserDto deactivateAccountByIdAndRole(Long ownerId, UserType userType);

    AuthUserDto activateAccountByIdAndRole(Long ownerId, UserType userType);

    AuthUserDto activateAccountByEmail(String email);

    AuthUserDto findByEmailAndSchool(String email, String schoolCode);
}
