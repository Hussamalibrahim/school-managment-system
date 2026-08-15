package com.SchoolManagementSystem.system.security.service;

import com.SchoolManagementSystem.system.security.dto.AuthUserDto;
import org.springframework.security.core.userdetails.UserDetails;
//TODO   there no use for this after Microservice

public interface JwtService {
    String generateToken(AuthUserDto user);

    String extractEmail(String token);

    String extractRole(String token);

    Long extractRefId(String token);

     boolean isValid(String token, UserDetails userDetails);
    Long extractSchoolId(String token);

    String extractSchoolCode(String token);


    }
