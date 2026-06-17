package com.SchoolManagementSystem.System.dtoMapper.student;

import com.SchoolManagementSystem.System.dto.student.StudentGuardianDto;
import com.SchoolManagementSystem.System.entity.student.StudentGuardian;

public final class StudentGuardianMapper {

    private StudentGuardianMapper() {}

    public static StudentGuardianDto toDto(StudentGuardian guardian) {
        if (guardian == null) return null;

        return new StudentGuardianDto(
                guardian.getId(),
                guardian.getCreatedAt(),
                guardian.getUpdatedAt(),
                guardian.getDeletedAt(),
                guardian.getPrimaryGuardian()
        );
    }

    public static StudentGuardian toEntity(StudentGuardianDto dto) {
        if (dto == null) return null;

        StudentGuardian guardian = new StudentGuardian();

        guardian.setId(dto.id());
        guardian.setCreatedAt(dto.createdAt());
        guardian.setUpdatedAt(dto.updatedAt());
        guardian.setDeletedAt(dto.deletedAt());
        guardian.setPrimaryGuardian(dto.primaryGuardian());

        return guardian;
    }
}