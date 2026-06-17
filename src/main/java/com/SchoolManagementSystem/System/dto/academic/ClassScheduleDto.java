package com.SchoolManagementSystem.System.dto.academic;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

public record ClassScheduleDto(

        Long id,

        Long classId,

        Long subjectId,

        Long teacherId,

        DayOfWeek dayOfWeek,

        Integer periodNumber,

        LocalTime startTime,

        LocalTime endTime

) implements Serializable {
}