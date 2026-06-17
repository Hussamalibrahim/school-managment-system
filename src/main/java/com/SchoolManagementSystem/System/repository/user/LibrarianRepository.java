package com.SchoolManagementSystem.System.repository.user;

import com.SchoolManagementSystem.System.entity.user.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, Long>
{
    Optional<Librarian> findByNationalId(String nationalId);
}