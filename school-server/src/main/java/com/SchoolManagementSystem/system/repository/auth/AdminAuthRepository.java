package com.SchoolManagementSystem.system.repository.auth;

import com.SchoolManagementSystem.system.entity.Auth.AdminAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminAuthRepository extends JpaRepository<AdminAuth , Long> {
    Optional<AdminAuth> findByEmail(String email);

    boolean existsByEmail(String email);
}
