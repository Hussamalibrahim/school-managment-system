package com.SchoolManagementSystem.System.dtoMapper.school;

import com.SchoolManagementSystem.System.dto.school.SchoolSettingsDto;
import com.SchoolManagementSystem.System.entity.school.SchoolSettings;

public final class SchoolSettingsMapper {

    private SchoolSettingsMapper() {}

    public static SchoolSettingsDto toDto(SchoolSettings settings) {
        if (settings == null) return null;

        return new SchoolSettingsDto(
                settings.getId(),
                settings.getCreatedAt(),
                settings.getUpdatedAt(),
                settings.getDeletedAt(),
                settings.getSchoolStartTime(),
                settings.getPeriodDurationMinutes(),
                settings.getBreakDurationMinutes(),
                settings.getMaxAllowedAbsences()
        );
    }

    public static SchoolSettings toEntity(SchoolSettingsDto dto) {
        if (dto == null) return null;

        SchoolSettings settings = new SchoolSettings();

        settings.setId(dto.id());
        settings.setCreatedAt(dto.createdAt());
        settings.setUpdatedAt(dto.updatedAt());
        settings.setDeletedAt(dto.deletedAt());
        settings.setSchoolStartTime(dto.schoolStartTime());
        settings.setPeriodDurationMinutes(dto.periodDurationMinutes());
        settings.setBreakDurationMinutes(dto.breakDurationMinutes());
        settings.setMaxAllowedAbsences(dto.maxAllowedAbsences());

        return settings;
    }
}