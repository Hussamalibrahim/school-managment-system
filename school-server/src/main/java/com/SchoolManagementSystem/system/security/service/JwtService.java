package com.SchoolManagementSystem.system.security.service;

import com.SchoolManagementSystem.system.dto.auth.AuthUserDto;
import com.SchoolManagementSystem.system.entity.Auth.AuthUser;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(AuthUser user);

    String extractEmail(String token);

    String extractRole(String token);

    Long extractRefId(String token);

     boolean isValid(String token, UserDetails userDetails);
    Long extractSchoolId(String token);

    String extractSchoolCode(String token);


    }
