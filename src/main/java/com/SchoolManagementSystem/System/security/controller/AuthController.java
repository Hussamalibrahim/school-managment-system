package com.SchoolManagementSystem.System.security.controller;

import com.SchoolManagementSystem.System.entity.enumeration.UserType;
import com.SchoolManagementSystem.System.security.dto.AuthRequest;
import com.SchoolManagementSystem.System.security.dto.AuthResponse;
import com.SchoolManagementSystem.System.security.AuthUserRepository;
import com.SchoolManagementSystem.System.security.dto.AuthUserDto;
import com.SchoolManagementSystem.System.security.dto.RegisterRequest;
import com.SchoolManagementSystem.System.security.service.AuthUserService;
import com.SchoolManagementSystem.System.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final AuthUserService authUserService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        AuthUserDto user = authUserService.findByEmail(request.email());

        String token = jwtService.generateToken(user);

        return new AuthResponse(token, user.role().name(), user.refId());
    }
    @PostMapping("/principle-register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
         log.info(request.toString());
        authUserService.register(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/deactivate-account/by-user")
    public ResponseEntity<AuthUserDto> deactivateAccountByIdAndRole(
            @RequestParam Long userId,
            @RequestParam UserType userType){

        return ResponseEntity.status(HttpStatus.OK).body(authUserService.deactivateAccountByIdAndRole(userId, userType));
    }
    @GetMapping("/deactivate-account/by-email")
    public ResponseEntity<AuthUserDto> deactivateAccountByEmail(@RequestParam String email){

        return ResponseEntity.ok(authUserService.deactivateAccountByEmail(email));
    }
    @GetMapping("/activate-account/by-user")
    public ResponseEntity<AuthUserDto> activateAccountByIdAndRole(
            @RequestParam Long userId,
            @RequestParam UserType userType){

        return ResponseEntity.ok(authUserService.activateAccountByIdAndRole(userId, userType));
    }
    @GetMapping("/activate-account/by-email")
    public ResponseEntity<AuthUserDto> activateAccountByEmail(@RequestParam String email){

        return ResponseEntity.ok(authUserService.activateAccountByEmail(email));
    }
}