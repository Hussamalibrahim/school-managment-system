package com.SchoolManagementSystem.system.mapper.academic;

import com.SchoolManagementSystem.system.dto.academic.EducationRecordDto;
import com.SchoolManagementSystem.system.entity.academic.EducationRecord;
import com.SchoolManagementSystem.system.entity.school.AcademicYear;
import com.SchoolManagementSystem.system.entity.student.Student;

public final class EducationRecordMapper {

    private EducationRecordMapper() {}


    public static EducationRecordDto toDto(EducationRecord entity) {

        return new EducationRecordDto(
                entity.getId(),
                entity.getStudent().getId(),
                entity.getAcademicYear().getId(),
                entity.getFinalAverage(),
                entity.getGradeLevel(),
                entity.getAbsenceDays(),
                entity.getPassed(),
                entity.getNotes()
        );
    }


    public static void updateEntity(EducationRecord educationRecord, EducationRecordDto dto) {
        educationRecord.setFinalAverage(dto.finalAverage());
        educationRecord.setPassed(dto.passed());
        educationRecord.setGradeLevel(dto.gradeLevel());
        educationRecord.setAbsenceDays(dto.absenceDays());
        educationRecord.setNotes(dto.notes());
    }
}