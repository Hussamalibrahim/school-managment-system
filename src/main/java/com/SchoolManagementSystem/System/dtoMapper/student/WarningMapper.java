package com.SchoolManagementSystem.System.dtoMapper.student;

import com.SchoolManagementSystem.System.dto.student.WarningDto;
import com.SchoolManagementSystem.System.entity.student.Warning;

public final class WarningMapper {

    private WarningMapper() {}

    public static WarningDto toDto(Warning warning) {
        if (warning == null) return null;

        return new WarningDto(
                warning.getId(),
                warning.getCreatedAt(),
                warning.getUpdatedAt(),
                warning.getDeletedAt(),
                warning.getWarningDate(),
                warning.getReason()
        );
    }

    public static Warning toEntity(WarningDto dto) {
        if (dto == null) return null;

        Warning warning = new Warning();

        warning.setId(dto.id());
        warning.setCreatedAt(dto.createdAt());
        warning.setUpdatedAt(dto.updatedAt());
        warning.setDeletedAt(dto.deletedAt());
        warning.setWarningDate(dto.warningDate());
        warning.setReason(dto.reason());

        return warning;
    }
}