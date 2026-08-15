package com.SchoolManagementSystem.system.mapper.school;

import com.SchoolManagementSystem.system.dto.request.DefineSchool;
import com.SchoolManagementSystem.system.dto.request.updateSchoolInfo;
import com.SchoolManagementSystem.system.dto.school.SchoolDto;
import com.SchoolManagementSystem.system.entity.school.School;

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

                school.getEducationStages(),
                school.getSchoolType()
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

        school.setEducationStages(dto.educationStages());
        school.setSchoolType(dto.schoolType());

        return school;
    }
    public static void fromDefineSchool(DefineSchool dto, School school) {

        school.setName(dto.name());
        school.setEducationStages(dto.educationStages());
        school.setSchoolType(dto.schoolType());

    }
    public static void updateEntity(updateSchoolInfo dto, School school) {
        school.setAddress(dto.address());
        school.setPhone(dto.phone());
        school.setLogoPath(dto.logoPath());
    }
}