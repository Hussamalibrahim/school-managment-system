package com.SchoolManagementSystem.system.repository.user;

import com.SchoolManagementSystem.system.entity.user.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>
{
    Optional<Teacher> findByNationalId(String nationalId);

    boolean existsByNationalId(String nationalId);
}