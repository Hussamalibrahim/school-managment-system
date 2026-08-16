package com.SchoolManagementSystem.system.security.controller;

import com.SchoolManagementSystem.system.entity.enumeration.UserType;
import com.SchoolManagementSystem.system.security.auth.TenantAuthenticationToken;
import com.SchoolManagementSystem.system.security.dto.AuthRequest;
import com.SchoolManagementSystem.system.security.dto.AuthResponse;
import com.SchoolManagementSystem.system.security.dto.AuthUserDto;
import com.SchoolManagementSystem.system.security.service.AuthUserService;
import com.SchoolManagementSystem.system.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AuthenticationManager authManager;
    private final AuthUserService authUserService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody AuthRequest request) {

        authManager.authenticate(
                new TenantAuthenticationToken(
                        request.email(),
                        request.password(),
                        null
                )
        );

        AuthUserDto user =
                authUserService.findByEmailAndSchool(
                        request.email(),
                        null);

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                token,
                user.role().name(),
                null,
                null);
    }
}