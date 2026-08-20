package com.SchoolManagementSystem.system.mapper.academic;

import com.SchoolManagementSystem.system.dto.academic.EducationRecordDto;
import com.SchoolManagementSystem.system.dto.academic.StudentEducationHistoryDto;
import com.SchoolManagementSystem.system.entity.academic.EducationRecord;

public final class EducationRecordMapper {

    private EducationRecordMapper() {
    }

    public static EducationRecordDto toDto(
            EducationRecord entity
    ) {

        return new EducationRecordDto(
                entity.getId(),
                entity.getStudent().getId(),
                entity.getAcademicYear().getId(),
                entity.getSchoolClass().getId(),
                entity.getFinalAverage(),
                entity.getGradeLevel(),
                entity.getAbsenceDays(),
                entity.getPassed(),
                entity.getRegisteredNextYear(),
                entity.getNotes()
        );
    }

    public static StudentEducationHistoryDto toHistoryDto(
            EducationRecord entity
    ) {

        return new StudentEducationHistoryDto(
                entity.getId(),
                entity.getAcademicYear().getId(),
                entity.getAcademicYear().getName(),
                entity.getSchoolClass().getId(),
                entity.getGradeLevel().name(),
                entity.getSchoolClass().getSection(),
                entity.getFinalAverage(),
                entity.getAbsenceDays(),
                entity.getPassed(),
                entity.getNotes()
        );
    }

    public static void updateEntity(
            EducationRecord entity,
            EducationRecordDto dto
    ) {

        entity.setFinalAverage(
                dto.finalAverage()
        );

        entity.setAbsenceDays(
                dto.absenceDays()
        );

        entity.setPassed(
                dto.passed()
        );

        entity.setRegisteredNextYear(
                dto.registeredNextYear()
        );

        entity.setNotes(
                dto.notes()
        );
    }
}