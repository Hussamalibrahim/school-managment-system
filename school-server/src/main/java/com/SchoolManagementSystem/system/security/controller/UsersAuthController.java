package com.SchoolManagementSystem.system.security.controller;

import com.SchoolManagementSystem.system.dto.auth.request.AuthRequest;
import com.SchoolManagementSystem.system.dto.auth.AuthUserDto;
import com.SchoolManagementSystem.system.dto.auth.response.AuthResponse;
import com.SchoolManagementSystem.system.entity.enumeration.UserType;
import com.SchoolManagementSystem.system.security.auth.TenantAuthenticationToken;
import com.SchoolManagementSystem.system.service.auth.AuthUserService;
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
public class UsersAuthController {

    private final AuthUserService authUserService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@PathVariable String schoolCode, @RequestBody AuthRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body(authUserService.login(schoolCode, request));
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