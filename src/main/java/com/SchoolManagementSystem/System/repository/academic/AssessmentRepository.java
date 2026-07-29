package com.SchoolManagementSystem.System.repository.academic;

import com.SchoolManagementSystem.System.entity.academic.Assessment;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    List<Assessment> findByTeacherId(Long teacherId);

    List<Assessment> findByClassScheduleIdAndSemesterId(Long classScheduleId, Long semesterId);

    List<Assessment> findByClassScheduleSchoolClassIdAndSemesterId(Long schoolClassId, Long semesterId);

    List<Assessment> findByClassScheduleSubjectId(Long subjectId);

    List<Assessment> findByClassScheduleTeacherId(Long teacherId);
}