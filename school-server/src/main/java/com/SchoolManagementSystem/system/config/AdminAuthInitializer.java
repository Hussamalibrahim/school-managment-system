package com.SchoolManagementSystem.system.config;

import com.SchoolManagementSystem.system.entity.admin.AdminAuth;
import com.SchoolManagementSystem.system.repository.admin.AdminAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminAuthInitializer implements CommandLineRunner {

    private final AdminAuthRepository adminAuthRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${main-admin.email}")
    private String email;

    @Value("${main-admin.password}")
    private String password;

    @Override
    public void run(String... args) {

        if (adminAuthRepository.existsByEmail(email)) {
            return;
        }

        AdminAuth admin = new AdminAuth();

        admin.setEmail(email);
        admin.setPassword(
                passwordEncoder.encode(password)
        );
        admin.setEnabled(true);

        adminAuthRepository.save(admin);
    }
}