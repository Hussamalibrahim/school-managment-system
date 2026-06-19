package com.SchoolManagementSystem.System.config;

import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import com.SchoolManagementSystem.System.entity.user.Principal;
import com.SchoolManagementSystem.System.repository.user.PrincipalRepository;
import com.SchoolManagementSystem.System.security.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PrincipalSeeder implements CommandLineRunner {

    private final AuthUserRepository authUserRepository;
    private final PrincipalRepository principalRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        String adminEmail = "admin";

        if (authUserRepository.findByEmail(adminEmail).isPresent()) {
            log.info("Principal already exists");
            return;
        }

        if (principalRepository.findByNationalId("258523").isPresent()) {
            log.info("Principal already exists in domain");
            return;
        }

        Principal principal = new Principal();
        principal.setNationalId("258523");

        principal = principalRepository.save(principal);

        AuthUser admin = new AuthUser();
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(Role.PRINCIPAL);
        admin.setRefId(principal.getId());

        authUserRepository.save(admin);

        log.info("Principal created successfully");
    }
}