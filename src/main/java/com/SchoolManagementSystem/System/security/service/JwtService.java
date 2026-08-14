package com.SchoolManagementSystem.System.security.service;

import com.SchoolManagementSystem.System.security.dto.AuthUserDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(AuthUserDto user);

    String extractEmail(String token);

    String extractRole(String token);

    Long extractRefId(String token);

     boolean isValid(String token, UserDetails userDetails);
    Long extractSchoolId(String token);

    String extractSchoolCode(String token);


    }
