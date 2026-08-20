package com.SchoolManagementSystem.system.repository.academic;

import com.SchoolManagementSystem.system.dto.academic.SemesterResultDto;
import com.SchoolManagementSystem.system.entity.academic.SemesterResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface SemesterResultRepository extends JpaRepository<SemesterResult, Long> {

    List<SemesterResult> findByStudentIdAndSemesterId(Long studentId, Long semesterId);

    List<SemesterResult> findByStudentIdAndSemesterAcademicYearId(Long studentId, Long academicYearId);

    Optional<SemesterResult> findByStudentIdAndSemesterIdAndSubjectId(Long studentId, Long semesterId, Long subjectId);

    List<SemesterResult> findBySemesterId(Long semesterId);
}