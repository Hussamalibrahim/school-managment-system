package com.SchoolManagementSystem.System.repository.user;

import com.SchoolManagementSystem.System.entity.user.Secretary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecretaryRepository extends JpaRepository<Secretary, Long>
{
    Optional<Secretary> findByNationalId(String nationalId);
}