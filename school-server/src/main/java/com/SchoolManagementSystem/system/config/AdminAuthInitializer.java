package com.SchoolManagementSystem.system.config;

import com.SchoolManagementSystem.system.entity.Auth.AdminAuth;
import com.SchoolManagementSystem.system.repository.auth.AdminAuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminAuthInitializer implements CommandLineRunner {

    private final AdminAuthRepository adminAuthRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${super-admin.email}")
    private String email;

    @Value("${super-admin.password}")
    private String password;

    @Override
    public void run(String... args) {

        if (adminAuthRepository.existsByEmail(email)) {
            log.info("Admin with email {} already exists. Skipping initialization.", email);
            return;
        }

        AdminAuth admin = new AdminAuth();

        admin.setEmail(email);
        admin.setPassword(
                passwordEncoder.encode(password)
        );
        admin.setEnabled(true);

        adminAuthRepository.save(admin);
        log.info("Admin with email {} has been initialized.", email);
    }
}