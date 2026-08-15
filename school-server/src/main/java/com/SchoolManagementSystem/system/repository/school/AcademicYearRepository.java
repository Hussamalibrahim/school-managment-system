package com.SchoolManagementSystem.system.repository.school;

import com.SchoolManagementSystem.system.entity.school.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long>
{
}