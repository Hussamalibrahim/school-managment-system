package com.SchoolManagementSystem.System.dto.academic;

import com.SchoolManagementSystem.System.entity.enumeration.ExamCategory;
import com.SchoolManagementSystem.System.entity.enumeration.SemesterName;

import java.time.LocalDateTime;

public record ExamDto(
        Long id,
        Long schoolClassId,
        Long subjectId,
        SemesterName semesterName,
        ExamCategory category,
        Double maxScore,
        Double weight,
        LocalDateTime examDateTime,
        Integer durationMinutes
) {
}