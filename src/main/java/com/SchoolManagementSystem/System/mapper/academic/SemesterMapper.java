package com.SchoolManagementSystem.System.mapper.academic;

import com.SchoolManagementSystem.System.dto.academic.SemesterDto;
import com.SchoolManagementSystem.System.dto.academic.SubjectDto;
import com.SchoolManagementSystem.System.entity.academic.Semester;
import com.SchoolManagementSystem.System.entity.academic.Subject;

public final class SemesterMapper {
    private SemesterMapper(){}
    public static SemesterDto toDto(Semester semester) {
        if (semester == null) return null;

        return new SemesterDto(
                semester.getId(),
                semester.getCreatedAt(),
                semester.getUpdatedAt(),
                semester.getDeletedAt(),
                semester.getSemesterName(),
                semester.getStartDate(),
                semester.getEndDate()
        );
    }

    public static Semester toEntity(SemesterDto dto) {
        if (dto == null) return null;

        Semester semester = new Semester();
        semester.setId(dto.id());
        semester.setCreatedAt(dto.createdAt());
        semester.setUpdatedAt(dto.updatedAt());
        semester.setDeletedAt(dto.deletedAt());

        semester.setSemesterName(dto.semesterName());
        semester.setStartDate(dto.startDate());
        semester.setEndDate(dto.endDate());

        return semester;
    }
    public static void updateEntity(Semester semester, SemesterDto dto) {

        semester.setSemesterName(dto.semesterName());
        semester.setStartDate(dto.startDate());
        semester.setEndDate(dto.endDate());
    }
}