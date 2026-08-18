package com.SchoolManagementSystem.system.repository.user;

import com.SchoolManagementSystem.system.entity.user.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, Long>
{
    Optional<Librarian> findByNationalId(String nationalId);

    boolean existsByNationalId(String nationalId);

    long countBySchoolId(Long schoolId);
}