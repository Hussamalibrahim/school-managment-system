package com.SchoolManagementSystem.system.controller.admin;

import com.SchoolManagementSystem.system.dto.auth.request.AdminLoginRequest;
import com.SchoolManagementSystem.system.dto.auth.response.AdminAuthResponse;
import com.SchoolManagementSystem.system.service.auth.AdminAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @PostMapping("/login")
    public ResponseEntity<AdminAuthResponse> login(@RequestBody AdminLoginRequest request) {

        return ResponseEntity.ok(adminAuthService.login(request));
    }
}