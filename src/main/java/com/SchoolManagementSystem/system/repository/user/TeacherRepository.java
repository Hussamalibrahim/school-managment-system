package com.SchoolManagementSystem.System.repository.user;

import com.SchoolManagementSystem.System.entity.user.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>
{
    Optional<Teacher> findByNationalId(String nationalId);

    boolean existsByNationalId(String nationalId);
}