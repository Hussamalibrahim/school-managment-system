package com.SchoolManagementSystem.System.security.service;

import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import com.SchoolManagementSystem.System.entity.enumeration.UserType;
import com.SchoolManagementSystem.System.entity.user.Principal;
import com.SchoolManagementSystem.System.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.System.exception.business.BusinessRuleException;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.mapper.user.PrincipalMapper;
import com.SchoolManagementSystem.System.repository.user.PrincipalRepository;
import com.SchoolManagementSystem.System.security.AuthUserRepository;
import com.SchoolManagementSystem.System.security.dto.RegisterRequest;
import com.SchoolManagementSystem.System.security.mapper.AuthUserMapper;
import com.SchoolManagementSystem.System.security.dto.AuthUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    @Transactional(readOnly = true)
    public AuthUserDto findByEmail(String email) {

        Optional<AuthUser> authUser = authUserRepository.findByEmail(email);
        return authUser.map(AuthUserMapper::toDto)
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public void register(RegisterRequest request) {

        if (authUserRepository.findByEmail(request.email()).isPresent()) {
            throw new AlreadyExistsException(
                    ErrorCode.EMAIL_ALREADY_EXISTS
            );
        }

        if (principalRepository.findByNationalId(request.nationalId()).isPresent()) {
            throw new AlreadyExistsException(
                    ErrorCode.NATIONAL_ID_ALREADY_EXISTS
            );
        }

        Principal principal = principalRepository.save(
                PrincipalMapper.fromRegisterRequest(request)
        );

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

        AuthUser user = findAuthUserByEmail(email);

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

        AuthUser user = findAuthUserByEmail(email);

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
                    enabled
                            ? ErrorCode.USER_ALREADY_ACTIVATED
                            : ErrorCode.USER_ALREADY_DEACTIVATED
            );
        }

        user.setEnabled(enabled);

        return authUserRepository.save(user);
    }

    private AuthUser findAuthUserByEmail(String email) {

        return authUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private AuthUser findAuthUser(
            Long refId,
            UserType type) {

        return authUserRepository
                .findAuthUserByRefIdAndRole(
                        refId,
                        Role.valueOf(type.name())
                )
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
