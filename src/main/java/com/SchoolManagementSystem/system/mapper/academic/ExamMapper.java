package com.SchoolManagementSystem.System.mapper.academic;

import com.SchoolManagementSystem.System.dto.academic.ExamDto;
import com.SchoolManagementSystem.System.dto.academic.request.ExamCreateRequest;
import com.SchoolManagementSystem.System.dto.academic.request.ExamUpdateRequest;
import com.SchoolManagementSystem.System.entity.academic.Exam;

public final class ExamMapper {

    private ExamMapper() {
    }

    public static ExamDto toDto(Exam exam) {
        return new ExamDto(
                exam.getId(),

                exam.getSchoolClass().getId(),

                exam.getSubject().getId(),

                exam.getSemester() != null ? exam.getSemester().getSemesterName() : null,

                exam.getCategory(),

                exam.getMaxScore(),
                exam.getWeight(),

                exam.getExamDateTime(),
                exam.getDurationMinutes()
        );
    }

    public static void fromCreateRequest(Exam exam, ExamCreateRequest request) {

        exam.setCategory(request.category());
        exam.setMaxScore(request.maxScore());
        exam.setWeight(request.weight());
        exam.setExamDateTime(request.examDateTime());
        exam.setDurationMinutes(request.durationMinutes());
    }

    public static void fromUpdateRequest(Exam exam, ExamUpdateRequest request) {

        exam.setCategory(request.category());
        exam.setMaxScore(request.maxScore());
        exam.setWeight(request.weight());
        exam.setExamDateTime(request.examDateTime());
        exam.setDurationMinutes(request.durationMinutes());
    }
}