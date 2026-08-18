package com.SchoolManagementSystem.system.repository.school;

import com.SchoolManagementSystem.system.entity.school.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {
    Optional<AcademicYear> findByCurrentYearTrue();

    Optional<AcademicYear> findBySchoolIdAndCurrentYearTrue(Long schoolId);
}