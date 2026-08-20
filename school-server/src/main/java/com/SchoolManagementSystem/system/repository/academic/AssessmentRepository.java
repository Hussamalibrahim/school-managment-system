package com.SchoolManagementSystem.system.repository.academic;

import com.SchoolManagementSystem.system.entity.academic.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    List<Assessment> findByTeacherId(Long teacherId);

    List<Assessment> findByClassScheduleIdAndSemesterId(Long classScheduleId, Long semesterId);

    List<Assessment> findByClassScheduleSchoolClassIdAndSemesterId(Long schoolClassId, Long semesterId);

    List<Assessment> findByClassScheduleSubjectId(Long subjectId);

    List<Assessment> findByClassScheduleTeacherId(Long teacherId);

    List<Assessment> findBySemesterId(Long semesterId);
}