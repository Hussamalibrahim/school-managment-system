package com.SchoolManagementSystem.system.dto.academic;

import com.SchoolManagementSystem.system.entity.enumeration.ExamCategory;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;

import java.time.LocalDateTime;

public record ExamDto(
        Long id,
        Long schoolClassId,
        Long subjectId,
        SemesterName semesterName,
        ExamCategory category,
        LocalDateTime examDateTime,
        Integer durationMinutes
) {
}