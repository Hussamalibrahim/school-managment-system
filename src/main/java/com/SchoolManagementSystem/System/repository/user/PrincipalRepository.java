package com.SchoolManagementSystem.System.repository.user;

import com.SchoolManagementSystem.System.entity.user.Principal;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface PrincipalRepository extends JpaRepository<Principal, Long>
{
    Optional<Principal> findByNationalId(String nationalId);
}