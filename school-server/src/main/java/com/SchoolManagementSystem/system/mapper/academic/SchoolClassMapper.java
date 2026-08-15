package com.SchoolManagementSystem.system.mapper.academic;

import com.SchoolManagementSystem.system.dto.academic.SchoolClassDto;
import com.SchoolManagementSystem.system.entity.academic.SchoolClass;

public final class SchoolClassMapper {

    private SchoolClassMapper() {}

    public static SchoolClassDto toDto(SchoolClass entity) {
        if (entity == null) return null;

        return new SchoolClassDto(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt(),

                entity.getSchool() != null ? entity.getSchool().getId() : null,

                entity.getGradeLevel(),
                entity.getSection(),
                entity.getLocation(),
                entity.getCapacity()
        );
    }

    public static SchoolClass toEntity(SchoolClassDto dto) {
        if (dto == null) return null;

        SchoolClass entity = new SchoolClass();

        entity.setId(dto.id());
        entity.setCreatedAt(dto.createdAt());
        entity.setUpdatedAt(dto.updatedAt());
        entity.setDeletedAt(dto.deletedAt());

        entity.setSchool(null);

        entity.setGradeLevel(dto.gradeLevel());
        entity.setSection(dto.section());
        entity.setLocation(dto.location());
        entity.setCapacity(dto.capacity());

        return entity;
    }
    public static void updateEntity(SchoolClass subject, SchoolClassDto dto) {

        subject.setGradeLevel(dto.gradeLevel());
        subject.setSection(dto.section());
        subject.setLocation(dto.location());
        subject.setCapacity(dto.capacity());
    }
}