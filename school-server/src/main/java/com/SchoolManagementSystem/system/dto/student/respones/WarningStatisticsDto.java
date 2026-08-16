package com.SchoolManagementSystem.system.dto.student.respones;

import com.SchoolManagementSystem.system.dto.student.WarningDto;

import java.util.List;

public record WarningStatisticsDto(
        Long studentId,
        String studentName,
        List<WarningDto> warnings
) {
}