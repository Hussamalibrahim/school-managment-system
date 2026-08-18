package com.SchoolManagementSystem.system.service.auth.impl;

import com.SchoolManagementSystem.system.dto.auth.AuthUserDto;
import com.SchoolManagementSystem.system.dto.auth.request.AuthRequest;
import com.SchoolManagementSystem.system.dto.auth.request.RegisterRequest;
import com.SchoolManagementSystem.system.dto.auth.response.AuthResponse;
import com.SchoolManagementSystem.system.entity.Auth.AuthUser;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import com.SchoolManagementSystem.system.entity.enumeration.UserType;
import com.SchoolManagementSystem.system.entity.user.Principal;
import com.SchoolManagementSystem.system.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.system.exception.business.BusinessRuleException;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.auth.AuthUserMapper;
import com.SchoolManagementSystem.system.mapper.user.PrincipalMapper;
import com.SchoolManagementSystem.system.repository.auth.AuthUserRepository;
import com.SchoolManagementSystem.system.repository.school.SchoolRepository;
import com.SchoolManagementSystem.system.repository.user.PrincipalRepository;
import com.SchoolManagementSystem.system.security.auth.TenantAuthenticationToken;
import com.SchoolManagementSystem.system.security.service.JwtService;
import com.SchoolManagementSystem.system.service.auth.AuthUserService;
import com.SchoolManagementSystem.system.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserRepository authUserRepository;
    private final PrincipalRepository principalRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final SchoolRepository schoolRepository;

    @Override
    @Transactional(readOnly = true)
    public AuthUserDto findByEmail(String email) {

        Optional<AuthUser> authUser = authUserRepository.findByEmail(email);
        return authUser.map(AuthUserMapper::toDto)
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public AuthUserDto findByEmailAndSchool(String email, String schoolCode) {
        Long schoolId = schoolRepository.findByCode(schoolCode)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND)).getId();

        Optional<AuthUser> authUser = authUserRepository.findByEmailAndSchoolId(email, schoolId);

        return authUser.map(AuthUserMapper::toDto)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public void register(RegisterRequest request) {

        Long schoolId = TenantContext.getSchoolId();

        if (schoolId == null) {
            throw new ValidationException(ErrorCode.SCHOOL_NOT_FOUND);
        }

        if (authUserRepository
                .findByEmailAndSchoolId(request.email(), schoolId).isPresent()) {
            throw new AlreadyExistsException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        if (principalRepository.findByNationalId(request.nationalId()).isPresent()) {
            throw new AlreadyExistsException(
                    ErrorCode.NATIONAL_ID_ALREADY_EXISTS
            );
        }

        Principal principal = principalRepository.save(
                PrincipalMapper.fromRegisterRequest(request));

        authUserRepository.save(
                AuthUserMapper.fromRegisterRequest(
                        request.email(),
                        passwordEncoder.encode(request.password()),
                        principal.getId(),
                        Role.PRINCIPAL)
                );

        log.info("Principal created successfully");
    }

    @Override
    @Transactional
    public AuthUserDto deactivateAccountByEmail(String email) {

        AuthUser user = findAuthUserByEmailAndSchoolId(email);

        return AuthUserMapper.toDto(
                updateAccountStatus(user, false)
        );
    }

    @Override
    @Transactional
    public AuthUserDto deactivateAccountByIdAndRole(Long ownerId, UserType userType) {
        AuthUser user = findAuthUser(ownerId, userType);

        return AuthUserMapper.toDto(
                updateAccountStatus(user, false)
        );
    }

    @Override
    @Transactional
    public AuthUserDto activateAccountByEmail(String email) {

        AuthUser user = findAuthUserByEmailAndSchoolId(email);

        return AuthUserMapper.toDto(
                updateAccountStatus(user, true)
        );
    }

    @Override
    @Transactional
    public AuthUserDto activateAccountByIdAndRole(Long ownerId, UserType userType) {
        AuthUser user = findAuthUser(ownerId, userType);

        return AuthUserMapper.toDto(
                updateAccountStatus(user, true)
        );
    }

    private AuthUser updateAccountStatus(
            AuthUser user,
            boolean enabled) {

        if (user.getEnabled() == enabled) {

            throw new BusinessRuleException(
                    enabled ? ErrorCode.USER_ALREADY_ACTIVATED
                            : ErrorCode.USER_ALREADY_DEACTIVATED);
        }

        user.setEnabled(enabled);

        return authUserRepository.save(user);
    }

    private AuthUser findAuthUserByEmailAndSchoolId(String email) {
        Long schoolId = TenantContext.getSchoolId();
        if (schoolId == null) {
            throw new ValidationException(ErrorCode.SCHOOL_NOT_FOUND);
        }
        return authUserRepository.findByEmailAndSchoolId(email, schoolId)
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private AuthUser findAuthUser(
            Long refId,
            UserType type) {

        return authUserRepository
                .findAuthUserByRefIdAndRole(refId, Role.valueOf(type.name()))
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public AuthResponse login(String schoolCode, AuthRequest request) {
        authManager.authenticate(
                new TenantAuthenticationToken(
                        request.email(),
                        request.password(),
                        schoolCode
                )
        );
        Long schoolId = TenantContext.getSchoolId();
        if (schoolId == null) {
            throw new ValidationException(ErrorCode.SCHOOL_NOT_FOUND);
        }
        AuthUser user = authUserRepository.findByEmailAndSchoolId(request.email(), schoolId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        String token = jwtService.generateToken(user);

        return AuthUserMapper.toAuthResponse(token,user);
    }
}
