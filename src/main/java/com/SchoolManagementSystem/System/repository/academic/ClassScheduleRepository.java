package com.SchoolManagementSystem.System.repository.academic;

import com.SchoolManagementSystem.System.entity.academic.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {

    List<ClassSchedule> findByTeacherId(Long teacherId);

    List<ClassSchedule> findStudentBySchoolClassId(Long classId);

    List<ClassSchedule> findBySchoolClassId(Long classId);

    long countBySchoolClassId(Long classId);
}