package com.SchoolManagementSystem.System.repository.student;

import com.SchoolManagementSystem.System.entity.student.Warning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarningRepository extends JpaRepository<Warning, Long>
{
    List<Warning> findByStudentId(Long studentId);
}