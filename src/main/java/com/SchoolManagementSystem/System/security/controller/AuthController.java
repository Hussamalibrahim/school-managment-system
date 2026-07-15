package com.SchoolManagementSystem.System.security.controller;

import com.SchoolManagementSystem.System.security.dto.AuthRequest;
import com.SchoolManagementSystem.System.security.dto.AuthResponse;
import com.SchoolManagementSystem.System.security.AuthUserRepository;
import com.SchoolManagementSystem.System.security.dto.AuthUserDto;
import com.SchoolManagementSystem.System.security.dto.RegisterRequest;
import com.SchoolManagementSystem.System.security.service.AuthUserService;
import com.SchoolManagementSystem.System.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final AuthUserRepository repo;
    private final AuthUserService authUserService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        AuthUserDto user = authUserService.findByEmail(request.email());

        String token = jwtService.generateToken(user);

        return new AuthResponse(token, user.role().name(), user.refId());
    }
    @PostMapping("/principle-register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        authUserService.register(request);
        return null;
    }
}