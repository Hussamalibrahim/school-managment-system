package com.SchoolManagementSystem.system.dto.academic.response;

public record TopStudentDto(
        Integer rank,
        Long studentId,
        String studentName,
        Double average
) {
}