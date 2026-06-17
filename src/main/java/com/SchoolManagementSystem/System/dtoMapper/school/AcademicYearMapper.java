package com.SchoolManagementSystem.System.dtoMapper.school;

import com.SchoolManagementSystem.System.dto.school.AcademicYearDto;
import com.SchoolManagementSystem.System.entity.school.AcademicYear;

public final class AcademicYearMapper {

    private AcademicYearMapper() {}

    public static AcademicYearDto toDto(AcademicYear academicYear) {
        if (academicYear == null) return null;

        return new AcademicYearDto(
                academicYear.getId(),
                academicYear.getCreatedAt(),
                academicYear.getUpdatedAt(),
                academicYear.getDeletedAt(),
                academicYear.getName(),
                academicYear.getStartDate(),
                academicYear.getEndDate(),
                academicYear.getCurrentYear()
        );
    }

    public static AcademicYear toEntity(AcademicYearDto dto) {
        if (dto == null) return null;

        AcademicYear academicYear = new AcademicYear();

        academicYear.setId(dto.id());
        academicYear.setCreatedAt(dto.createdAt());
        academicYear.setUpdatedAt(dto.updatedAt());
        academicYear.setDeletedAt(dto.deletedAt());
        academicYear.setName(dto.name());
        academicYear.setStartDate(dto.startDate());
        academicYear.setEndDate(dto.endDate());
        academicYear.setCurrentYear(dto.currentYear());

        return academicYear;
    }
}