package com.SchoolManagementSystem.system.service.auth.impl;
import com.SchoolManagementSystem.system.dto.auth.request.AdminLoginRequest;
import com.SchoolManagementSystem.system.dto.auth.response.AdminAuthResponse;
import com.SchoolManagementSystem.system.entity.Auth.AdminAuth;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.repository.auth.AdminAuthRepository;
import com.SchoolManagementSystem.system.security.jwt.AdminJwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private final AdminAuthRepository adminAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminJwtService adminJwtService;

    public AdminAuthResponse login(AdminLoginRequest request) {

        AdminAuth admin = adminAuthRepository.findByEmail(request.email())
                        .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        if (!Boolean.TRUE.equals(admin.getEnabled())) {
            throw new ValidationException(ErrorCode.ACCOUNT_NOT_ACTIVATED_YET);
        }

        if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
            throw new ValidationException(ErrorCode.INVALID_CREDENTIALS);
        }

        String token = adminJwtService.generateToken(admin.getEmail());

        return new AdminAuthResponse(token, Role.SUPER_ADMIN.name());
    }
}