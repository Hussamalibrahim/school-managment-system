package com.SchoolManagementSystem.System.dtoMapper.academic;

import com.SchoolManagementSystem.System.dto.academic.EducationRecordDto;
import com.SchoolManagementSystem.System.entity.academic.EducationRecord;

public final class EducationRecordMapper {
    private EducationRecordMapper() {
    }
    public static EducationRecordDto toDto(EducationRecord record) {
        if (record == null) return null;

        return new EducationRecordDto(
                record.getId(),
                record.getCreatedAt(),
                record.getUpdatedAt(),
                record.getDeletedAt(),
                record.getSchoolName(),
                record.getOverallResult(),
                record.getNotes()
        );
    }

    public static EducationRecord toEntity(EducationRecordDto dto) {
        if (dto == null) return null;

        EducationRecord record = new EducationRecord();
        record.setId(dto.id());
        record.setCreatedAt(dto.createdAt());
        record.setUpdatedAt(dto.updatedAt());
        record.setDeletedAt(dto.deletedAt());
        record.setSchoolName(dto.schoolName());
        record.setOverallResult(dto.overallResult());
        record.setNotes(dto.notes());

        return record;
    }
}
