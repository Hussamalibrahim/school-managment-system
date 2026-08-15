package com.SchoolManagementSystem.System.repository.academic;

import com.SchoolManagementSystem.System.entity.academic.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult,Long> {
    Optional<ExamResult> findByExamIdAndStudentId(Long examId, Long studentId);

    List<ExamResult> findByExamId(Long examId);

}
