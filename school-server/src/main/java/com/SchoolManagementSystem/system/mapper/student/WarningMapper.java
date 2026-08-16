package com.SchoolManagementSystem.system.mapper.student;

import com.SchoolManagementSystem.system.dto.student.WarningDto;
import com.SchoolManagementSystem.system.entity.student.Warning;

public final class WarningMapper {

    private WarningMapper() {}

    public static WarningDto toDto(Warning warning) {
        if (warning == null) return null;

        return new WarningDto(
                warning.getId(),
                warning.getStudent() != null ? warning.getStudent().getSchool().getId() : null,
                warning.getReason(),
                warning.getMessage(),
                warning.getWarningDate(),
                warning.getCreatedAt(),
                warning.getUpdatedAt(),
                warning.getDeletedAt()
        );
    }

    public static Warning toEntity(WarningDto dto) {
        if (dto == null) return null;

        Warning warning = new Warning();

        warning.setId(dto.id());
        warning.setReason(dto.reason());
        warning.setWarningDate(dto.warningDate());
        warning.setMessage(dto.message());
        warning.setWarningDate(dto.warningDate());
        warning.setCreatedAt(dto.createdAt());
        warning.setUpdatedAt(dto.updatedAt());
        warning.setDeletedAt(dto.deletedAt());

        return warning;
    }
}