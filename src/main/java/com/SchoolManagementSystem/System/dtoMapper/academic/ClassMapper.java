package com.SchoolManagementSystem.System.dtoMapper.academic;


import com.SchoolManagementSystem.System.dto.academic.ClassDto;
import com.SchoolManagementSystem.System.entity.academic.Class;


public final class ClassMapper {
private ClassMapper() {}
    public static ClassDto toDto(Class clazz) {
        if (clazz == null) return null;

        return new ClassDto(
                clazz.getId(),
                clazz.getCreatedAt(),
                clazz.getUpdatedAt(),
                clazz.getDeletedAt(),
                clazz.getLevel(),
                clazz.getSection(),
                clazz.getLocation(),
                clazz.getCapacity()
        );
    }

    public static Class toEntity(ClassDto dto) {
        if (dto == null) return null;

        Class clazz = new Class();
        clazz.setId(dto.id());
        clazz.setCreatedAt(dto.createdAt());
        clazz.setUpdatedAt(dto.updatedAt());
        clazz.setDeletedAt(dto.deletedAt());
        clazz.setLevel(dto.level());
        clazz.setSection(dto.section());
        clazz.setLocation(dto.location());
        clazz.setCapacity(dto.capacity());

        return clazz;
    }
}