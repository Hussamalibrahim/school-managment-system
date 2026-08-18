package com.SchoolManagementSystem.system.mapper.academic;

import com.SchoolManagementSystem.system.dto.academic.SemesterDto;
import com.SchoolManagementSystem.system.dto.academic.request.SemesterUpdateRequest;
import com.SchoolManagementSystem.system.entity.academic.Semester;

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
    public static void updateEntity(Semester semester, SemesterUpdateRequest dto) {

        semester.setSemesterName(dto.semesterName());
        semester.setStartDate(dto.startDate());
        semester.setEndDate(dto.endDate());
    }
}