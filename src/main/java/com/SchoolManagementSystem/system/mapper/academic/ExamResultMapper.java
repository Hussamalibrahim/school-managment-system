package com.SchoolManagementSystem.System.mapper.academic;

import com.SchoolManagementSystem.System.dto.academic.ExamResultDto;
import com.SchoolManagementSystem.System.entity.academic.ExamResult;

public class ExamResultMapper {

    private ExamResultMapper() {
    }

    public static ExamResultDto toDto(ExamResult examResult) {
        if (examResult == null) {
            return null;
        }

        return new ExamResultDto(
                examResult.getId(),
                examResult.getExam().getId(),
                examResult.getStudent().getId(),
                examResult.getScore()
        );
    }
}
