package com.SchoolManagementSystem.system.mapper.academic;

import com.SchoolManagementSystem.system.dto.academic.ExamDto;
import com.SchoolManagementSystem.system.dto.academic.request.ExamCreateRequest;
import com.SchoolManagementSystem.system.dto.academic.request.ExamUpdateRequest;
import com.SchoolManagementSystem.system.entity.academic.Exam;

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
                exam.getExamDateTime(),
                exam.getDurationMinutes()
        );
    }

    public static void fromCreateRequest(Exam exam, ExamCreateRequest request) {

        exam.setCategory(request.category());
        exam.setExamDateTime(request.examDateTime());
        exam.setDurationMinutes(request.durationMinutes());
    }

    public static void fromUpdateRequest(Exam exam, ExamUpdateRequest request) {

        exam.setCategory(request.category());
        exam.setExamDateTime(request.examDateTime());
        exam.setDurationMinutes(request.durationMinutes());
    }
}