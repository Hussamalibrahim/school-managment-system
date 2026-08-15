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
@RequestMapping("/api/{schoolCode}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final AuthUserService authUserService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public AuthResponse login(
            @PathVariable String schoolCode,
            @RequestBody AuthRequest request) {

        authManager.authenticate(
                new TenantAuthenticationToken(
                        request.email(),
                        request.password(),
                        schoolCode
                )
        );

        AuthUserDto user =
                authUserService.findByEmailAndSchool(
                        request.email(),
                        schoolCode);

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                token,
                user.role().name(),
                user.refId(),
                user.schoolId());
    }

    @GetMapping("/deactivate-account/by-user")
    public ResponseEntity<AuthUserDto> deactivateAccountByIdAndRole(
            @RequestParam Long userId,
            @RequestParam UserType userType) {

        return ResponseEntity.status(HttpStatus.OK).body(authUserService.deactivateAccountByIdAndRole(userId, userType));
    }

    @GetMapping("/deactivate-account/by-email")
    public ResponseEntity<AuthUserDto> deactivateAccountByEmail(@RequestParam String email) {

        return ResponseEntity.ok(authUserService.deactivateAccountByEmail(email));
    }

    @GetMapping("/activate-account/by-user")
    public ResponseEntity<AuthUserDto> activateAccountByIdAndRole(
            @RequestParam Long userId,
            @RequestParam UserType userType) {

        return ResponseEntity.ok(authUserService.activateAccountByIdAndRole(userId, userType));
    }

    @GetMapping("/activate-account/by-email")
    public ResponseEntity<AuthUserDto> activateAccountByEmail(@RequestParam String email) {

        return ResponseEntity.ok(authUserService.activateAccountByEmail(email));
    }
}