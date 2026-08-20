package com.SchoolManagementSystem.system.repository.academic;

import com.SchoolManagementSystem.system.entity.academic.EducationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EducationRecordRepository extends JpaRepository<EducationRecord, Long> {
    Optional<EducationRecord> findByStudentIdAndAcademicYearId(Long studentId, Long academicYearId);

    List<EducationRecord> findByStudentIdOrderByAcademicYearStartDateDesc(Long studentId);

    List<EducationRecord> findByAcademicYearId(Long academicYearId);
}