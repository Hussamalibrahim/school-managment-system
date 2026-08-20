package com.SchoolManagementSystem.system.repository.academic;

import com.SchoolManagementSystem.system.entity.academic.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult,Long> {
    Optional<ExamResult> findByExamIdAndStudentId(Long examId, Long studentId);

    List<ExamResult> findByExamId(Long examId);

    List<ExamResult> findByExamSemesterId(Long semesterId);

    @Query("""
    SELECT er
    FROM ExamResult er
    JOIN FETCH er.exam e
    JOIN FETCH e.subject s
    JOIN FETCH e.semester sem
    WHERE er.student.id = :studentId
      AND sem.academicYear.id = :academicYearId
""")
    List<ExamResult> findByStudentIdAndAcademicYearId(
            @Param("studentId") Long studentId,
            @Param("academicYearId") Long academicYearId
    );

    @Query("""
    SELECT er
    FROM ExamResult er
    JOIN FETCH er.exam e
    JOIN FETCH e.subject s
    WHERE er.student.id = :studentId
      AND e.semester.id = :semesterId
      AND s.id = :subjectId
""")
    List<ExamResult> findByStudentAndSemesterAndSubject(
            @Param("studentId") Long studentId,
            @Param("semesterId") Long semesterId,
            @Param("subjectId") Long subjectId
    );

    List<ExamResult> findByStudentId(Long studentId);
}
