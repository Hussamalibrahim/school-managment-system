package com.SchoolManagementSystem.System.dtoMapper.academic;

import com.SchoolManagementSystem.System.dto.academic.SemesterDto;
import com.SchoolManagementSystem.System.entity.academic.Semester;

public final class SemesterMapper {
    private SemesterMapper(){}
    public static SemesterDto toDto(Semester semester) {
        if (semester == null) return null;

        return new SemesterDto(
                semester.getId(),
                semester.getCreatedAt(),
                semester.getUpdatedAt(),
                semester.getDeletedAt(),
                semester.getName(),
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
        semester.setName(dto.name());
        semester.setStartDate(dto.startDate());
        semester.setEndDate(dto.endDate());

        return semester;
    }
}