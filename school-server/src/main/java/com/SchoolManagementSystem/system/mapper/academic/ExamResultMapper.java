package com.SchoolManagementSystem.system.mapper.academic;

import com.SchoolManagementSystem.system.dto.academic.ExamResultDto;
import com.SchoolManagementSystem.system.entity.academic.ExamResult;

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
