package com.SchoolManagementSystem.system.repository.academic;

import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SchoolManagementSystem.system.entity.academic.SchoolClass;


@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    boolean existsByGradeLevelAndSection(GradeLevel gradeLevel, String section);

}