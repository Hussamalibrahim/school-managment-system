package com.SchoolManagementSystem.system.repository.academic;

import com.SchoolManagementSystem.system.entity.academic.ClassSchedule;
import com.SchoolManagementSystem.system.entity.academic.SchoolClass;
import com.SchoolManagementSystem.system.entity.academic.Subject;
import com.SchoolManagementSystem.system.entity.enumeration.PeriodNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {

    List<ClassSchedule> findByTeacherId(Long teacherId);

    long countBySchoolClassId(Long classId);

    List<ClassSchedule> findClassScheduleBySchoolClass_Id(Long schoolClassId);

    boolean existsByTeacherIdAndDayOfWeekAndPeriodNumberAndIdNot(Long teacherId, DayOfWeek dayOfWeek, PeriodNumber periodNumber, Long id);

    boolean existsBySchoolClassIdAndDayOfWeekAndPeriodNumberAndIdNot(Long schoolClassId, DayOfWeek dayOfWeek, PeriodNumber periodNumber, Long id);

    boolean existsByTeacherIdAndDayOfWeekAndPeriodNumber(Long teacherId, DayOfWeek dayOfWeek, PeriodNumber periodNumber);

    boolean existsBySchoolClassIdAndDayOfWeekAndPeriodNumber(Long id, DayOfWeek dayOfWeek, PeriodNumber periodNumber);

    List<ClassSchedule> findClassScheduleBySchoolClass(SchoolClass studentSchoolClass);

    boolean existsBySubject(Subject subject);

    boolean existsByTeacherIdAndSchoolClassId(Long teacherId, Long classId);
}