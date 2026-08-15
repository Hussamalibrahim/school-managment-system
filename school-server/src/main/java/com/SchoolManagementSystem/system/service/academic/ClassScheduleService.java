package com.SchoolManagementSystem.system.service.academic;

import com.SchoolManagementSystem.system.dto.academic.ClassScheduleDto;
import com.SchoolManagementSystem.system.dto.student.StudentDto;
import com.SchoolManagementSystem.system.security.UserPrincipal;

import java.time.DayOfWeek;
import java.util.List;

public interface ClassScheduleService {

    List<ClassScheduleDto> getAll();
    List<ClassScheduleDto> getByTeacher(Long teacherId);
    List<ClassScheduleDto> getBySchoolClass(Long classId);
    List<StudentDto> getStudentsByTeacher(Long teacherId);
    List<ClassScheduleDto> addExtraPeriod(Long classId, DayOfWeek day);
    ClassScheduleDto assignTeacher(Long scheduleId, Long teacherId, Long subjectId);

    List<ClassScheduleDto> getMySchedule(UserPrincipal user);

}