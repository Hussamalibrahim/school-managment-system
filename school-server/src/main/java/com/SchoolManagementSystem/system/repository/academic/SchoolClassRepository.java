package com.SchoolManagementSystem.system.repository.academic;

import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SchoolManagementSystem.system.entity.academic.SchoolClass;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    boolean existsByGradeLevelAndSection(GradeLevel gradeLevel, String section);
    Optional<SchoolClass> findFirstByGradeLevel(
            GradeLevel gradeLevel
    );
    Optional<SchoolClass>

    findByGradeLevelAndSection(GradeLevel gradeLevel, String section);

    List<SchoolClass> findByGradeLevel(GradeLevel next);
}