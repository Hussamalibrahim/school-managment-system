package com.SchoolManagementSystem.system.repository.student;

import com.SchoolManagementSystem.system.entity.student.Warning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarningRepository extends JpaRepository<Warning, Long>
{
}