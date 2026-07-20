package com.SchoolManagementSystem.System.repository.academic;

import com.SchoolManagementSystem.System.entity.academic.Subject;
import com.SchoolManagementSystem.System.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.System.entity.enumeration.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>
{
    Optional<Subject> findSubjectByName(String name);

    List<Subject> findSubjectBySemester(Semester semester);

    List<Subject> findSubjectByGradeLevel(GradeLevel gradeLevel);

    List<Subject> findByGradeLevelAndSemester(GradeLevel gradeLevel, Semester semester);
}