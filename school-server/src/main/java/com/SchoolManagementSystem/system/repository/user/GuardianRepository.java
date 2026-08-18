package com.SchoolManagementSystem.system.repository.user;

import com.SchoolManagementSystem.system.entity.user.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Long>
{
    Optional<Guardian> findByNationalId(String nationalId);

    boolean existsByNationalIdAndIdNot(String nationalId, Long id);

    boolean existsByNationalId(String nationalId);

    long countBySchoolId(Long schoolId);
}