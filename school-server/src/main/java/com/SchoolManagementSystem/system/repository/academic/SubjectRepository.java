package com.SchoolManagementSystem.system.repository.academic;

import com.SchoolManagementSystem.system.entity.academic.Subject;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>
{
    Optional<Subject> findSubjectByName(String name);

    List<Subject> findSubjectBySemesterName(SemesterName semesterName);

    List<Subject> findSubjectByGradeLevel(GradeLevel gradeLevel);

    List<Subject> findByGradeLevelAndSemesterName(GradeLevel gradeLevel, SemesterName semesterName);

    boolean existsByName(String name);
}