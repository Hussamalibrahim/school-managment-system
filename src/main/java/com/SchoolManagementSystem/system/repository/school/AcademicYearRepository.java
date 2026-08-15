package com.SchoolManagementSystem.System.repository.school;

import com.SchoolManagementSystem.System.entity.school.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long>
{
}