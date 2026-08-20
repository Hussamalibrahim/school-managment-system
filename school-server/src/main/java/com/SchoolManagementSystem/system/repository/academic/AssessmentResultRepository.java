package com.SchoolManagementSystem.system.repository.academic;

import com.SchoolManagementSystem.system.entity.academic.AssessmentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Long> {

    List<AssessmentResult> findByAssessmentId(Long assessmentId);

    List<AssessmentResult> findByStudentId(Long studentId);

    List<AssessmentResult> findByStudentIdAndAssessmentId(Long studentId, Long assessmentId);

    boolean existsByStudentIdAndAssessmentId(Long studentId, Long assessmentId);

    @Query("""
    SELECT ar
    FROM AssessmentResult ar
    JOIN FETCH ar.assessment a
    JOIN FETCH a.classSchedule cs
    JOIN FETCH cs.subject s
    JOIN FETCH a.semester sem
    WHERE ar.student.id = :studentId
      AND sem.academicYear.id = :academicYearId
""")
    List<AssessmentResult> findByStudentIdAndAcademicYearId(
            @Param("studentId") Long studentId,
            @Param("academicYearId") Long academicYearId
    );

    @Query("""
    SELECT ar
    FROM AssessmentResult ar
    JOIN FETCH ar.assessment a
    JOIN FETCH a.classSchedule cs
    JOIN FETCH cs.subject s
    WHERE ar.student.id = :studentId
      AND a.semester.id = :semesterId
      AND s.id = :subjectId
""")
    List<AssessmentResult> findByStudentAndSemesterAndSubject(
            @Param("studentId") Long studentId,
            @Param("semesterId") Long semesterId,
            @Param("subjectId") Long subjectId
    );

    List<AssessmentResult> findByAssessmentSemesterId(Long semesterId);
}