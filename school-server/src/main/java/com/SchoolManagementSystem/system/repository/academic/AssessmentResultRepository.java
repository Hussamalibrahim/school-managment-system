package com.SchoolManagementSystem.system.repository.academic;

import com.SchoolManagementSystem.system.entity.academic.AssessmentResult;
import org.springframework.data.jpa.repository.JpaRepository;
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

    List<AssessmentResult> findByAssessmentSemesterId(Long semesterId);
}