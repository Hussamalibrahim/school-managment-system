package com.SchoolManagementSystem.System.mapper.school;

import com.SchoolManagementSystem.System.dto.school.SchoolDto;
import com.SchoolManagementSystem.System.entity.school.School;

public final class SchoolMapper {

    private SchoolMapper() {}

    public static SchoolDto toDto(School school) {
        if (school == null) return null;

        return new SchoolDto(
                school.getId(),
                school.getCreatedAt(),
                school.getUpdatedAt(),
                school.getDeletedAt(),

                school.getName(),
                school.getAddress(),
                school.getPhone(),
                school.getLogoPath(),

                school.getSupportedStages(),
                school.getSchoolCategory()
        );
    }

    public static School toEntity(SchoolDto dto) {
        if (dto == null) return null;

        School school = new School();

        school.setId(dto.id());
        school.setCreatedAt(dto.createdAt());
        school.setUpdatedAt(dto.updatedAt());
        school.setDeletedAt(dto.deletedAt());

        school.setName(dto.name());
        school.setAddress(dto.address());
        school.setPhone(dto.phone());
        school.setLogoPath(dto.logoPath());

        school.setSupportedStages(dto.supportedStages());
        school.setSchoolCategory(dto.schoolCategory());

        return school;
    }
}