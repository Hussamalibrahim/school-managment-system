package com.SchoolManagementSystem.System.service.academic;

import com.SchoolManagementSystem.System.dto.academic.ClassScheduleDto;
import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.entity.academic.ClassSchedule;

import java.time.DayOfWeek;
import java.util.List;

public interface ClassScheduleService {

    List<ClassScheduleDto> getAll();
    List<ClassScheduleDto> getByTeacher(Long teacherId);
    List<ClassScheduleDto> getByClass(Long classId);
    List<StudentDto> getStudentsByTeacher(Long teacherId);
    List<ClassScheduleDto> addExtraPeriod(Long classId, DayOfWeek day);
    ClassScheduleDto assignTeacher(Long scheduleId, Long teacherId, Long subjectId);
}