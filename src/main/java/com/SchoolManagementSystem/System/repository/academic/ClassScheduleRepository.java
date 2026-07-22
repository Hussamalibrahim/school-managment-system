package com.SchoolManagementSystem.System.repository.academic;

import com.SchoolManagementSystem.System.entity.academic.ClassSchedule;
import com.SchoolManagementSystem.System.entity.enumeration.PeriodNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.Collection;
import java.util.List;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {

    List<ClassSchedule> findByTeacherId(Long teacherId);

    long countBySchoolClassId(Long classId);

    List<ClassSchedule> findClassScheduleBySchoolClass_Id(Long schoolClassId);

    boolean existsByTeacherIdAndDayOfWeekAndPeriodNumber(Long teacherId, DayOfWeek dayOfWeek, PeriodNumber periodNumber);

    boolean existsBySchoolClassIdAndDayOfWeekAndPeriodNumber(Long id, DayOfWeek dayOfWeek, PeriodNumber periodNumber);
}