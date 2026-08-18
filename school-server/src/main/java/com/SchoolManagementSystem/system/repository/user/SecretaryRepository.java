package com.SchoolManagementSystem.system.repository.user;

import com.SchoolManagementSystem.system.entity.user.Secretary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecretaryRepository extends JpaRepository<Secretary, Long>
{
    Optional<Secretary> findByNationalId(String nationalId);

    boolean existsByNationalId(String nationalId);

    long countBySchoolId(Long schoolId);
}