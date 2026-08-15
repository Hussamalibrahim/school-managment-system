package com.SchoolManagementSystem.System.dto.academic.respones;

import com.SchoolManagementSystem.System.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.System.entity.enumeration.PeriodNumber;

import java.time.DayOfWeek;
import java.time.LocalDateTime;


public record ClassScheduleResponse(
        Long id,

        Long schoolClassId,
        GradeLevel gradeLevel,
        String section,

        Long subjectId,
        String subjectName,

        Long teacherId,
        String teacherName,

        DayOfWeek dayOfWeek,
        PeriodNumber periodNumber,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}