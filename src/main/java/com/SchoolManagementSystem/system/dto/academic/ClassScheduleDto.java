package com.SchoolManagementSystem.System.dto.academic;

import java.time.DayOfWeek;
import com.SchoolManagementSystem.System.entity.enumeration.PeriodNumber;
import java.time.LocalDateTime;

public record ClassScheduleDto(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,

        Long schoolClassId,
        Long subjectId,
        Long teacherId,

        DayOfWeek dayOfWeek,
        PeriodNumber periodNumber
) {
}