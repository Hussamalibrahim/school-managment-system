package com.SchoolManagementSystem.System.repository.user;

import com.SchoolManagementSystem.System.entity.user.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Long>
{
    Optional<Guardian> findByNationalId(String nationalId);

}