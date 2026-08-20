package com.SchoolManagementSystem.system.mapper.academic;

import com.SchoolManagementSystem.system.dto.academic.SemesterResultDto;
import com.SchoolManagementSystem.system.entity.academic.SemesterResult;

public final class SemesterResultMapper {

    private SemesterResultMapper() {
    }

    public static SemesterResultDto toDto(SemesterResult result) {

        return new SemesterResultDto(
                result.getId(),
                result.getStudent().getId(),
                result.getSemester().getId(),
                result.getSubject().getId(),
                result.getSubject().getName(),
                result.getContinuousAverage(),
                result.getExamScore(),
                result.getFinalScore()
        );
    }
}