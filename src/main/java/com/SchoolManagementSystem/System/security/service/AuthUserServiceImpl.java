package com.SchoolManagementSystem.System.security.service;

import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import com.SchoolManagementSystem.System.entity.user.Principal;
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
@Transactional
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserRepository authUserRepository;
    private final PrincipalRepository principalRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthUserDto findByEmail(String email) {

        Optional<AuthUser> authUser = authUserRepository.findByEmail(email);
        return authUser.map(AuthUserMapper::toDto).orElse(null);
    }

    @Override
    public void register(RegisterRequest request) {

        if (authUserRepository.findByEmail(request.email()).isPresent()) {
            log.info("Principal already exists");
            return;
        }

        if (principalRepository.findByNationalId(request.nationalId()).isPresent()) {
            log.info("Principal already exists in domain");
            return;
        }

        Principal principal = new Principal();
        principal.setNationalId(request.nationalId());
        principal.setFirstName(request.firstName());
        principal.setFirstName(request.lastName());

        principal = principalRepository.save(principal);

        AuthUser admin = new AuthUser();
        admin.setEmail(request.email());
        admin.setPassword(passwordEncoder.encode(request.password()));
        admin.setRole(Role.PRINCIPAL);
        admin.setRefId(principal.getId());

        authUserRepository.save(admin);

        log.info("Principal created successfully");
    }
}
