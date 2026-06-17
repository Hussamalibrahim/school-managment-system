package com.SchoolManagementSystem.System.dtoMapper.academic;

import com.SchoolManagementSystem.System.dto.academic.ClassScheduleDto;
import com.SchoolManagementSystem.System.entity.academic.Class;
import com.SchoolManagementSystem.System.entity.academic.ClassSchedule;
import com.SchoolManagementSystem.System.entity.academic.Subject;
import com.SchoolManagementSystem.System.entity.user.Teacher;

public final class ClassScheduleMapper
{
    private ClassScheduleMapper()
    {
    }

    public static ClassScheduleDto toDto(ClassSchedule schedule)
    {
        return new ClassScheduleDto(
                schedule.getId(),
                schedule.getStudentClass().getId(),
                schedule.getSubject().getId(),
                schedule.getTeacher().getId(),
                schedule.getDayOfWeek(),
                schedule.getPeriodNumber(),
                schedule.getStartTime(),
                schedule.getEndTime()
        );
    }

    public static ClassSchedule toEntity(
            ClassScheduleDto dto,
            Class studentClass,
            Subject subject,
            Teacher teacher)
    {
        ClassSchedule schedule = new ClassSchedule();

        schedule.setStudentClass(studentClass);
        schedule.setSubject(subject);
        schedule.setTeacher(teacher);

        schedule.setDayOfWeek(dto.dayOfWeek());
        schedule.setPeriodNumber(dto.periodNumber());

        schedule.setStartTime(dto.startTime());
        schedule.setEndTime(dto.endTime());

        return schedule;
    }
}