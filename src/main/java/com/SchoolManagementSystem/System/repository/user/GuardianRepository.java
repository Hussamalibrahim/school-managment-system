package com.SchoolManagementSystem.System.repository.user;

import com.SchoolManagementSystem.System.entity.user.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Long>
{
    Optional<Guardian> findByNationalId(String nationalId);

    @Query("""
       SELECT g
       FROM Guardian g
       WHERE NOT EXISTS (
           SELECT sg
           FROM StudentGuardian sg
           WHERE sg.guardian = g
       )
       """)
    List<Guardian> findGuardiansWithoutStudents();
}