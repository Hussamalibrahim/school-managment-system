package com.SchoolManagementSystem.system.mapper.academic;

import com.SchoolManagementSystem.system.dto.academic.EducationRecordDto;
import com.SchoolManagementSystem.system.entity.academic.EducationRecord;
import com.SchoolManagementSystem.system.entity.school.AcademicYear;
import com.SchoolManagementSystem.system.entity.student.Student;

public final class EducationRecordMapper {

    private EducationRecordMapper() {}

    public static EducationRecordDto toDto(EducationRecord educationRecord) {
        if (educationRecord == null) return null;

        return new EducationRecordDto(
                educationRecord.getId(),
                educationRecord.getCreatedAt(),
                educationRecord.getUpdatedAt(),
                educationRecord.getDeletedAt(),

                educationRecord.getStudent() != null ? educationRecord.getStudent().getId() : null,
                educationRecord.getAcademicYear() != null ? educationRecord.getAcademicYear().getId() : null,

                educationRecord.getFinalAverage(),
                educationRecord.getAbsenceDays(),
                educationRecord.getPassed(),
                educationRecord.getNotes()
        );
    }

    public static EducationRecord toEntity(EducationRecordDto dto) {
        if (dto == null) return null;

        EducationRecord educationRecord = new EducationRecord();

        educationRecord.setId(dto.id());
        educationRecord.setCreatedAt(dto.createdAt());
        educationRecord.setUpdatedAt(dto.updatedAt());
        educationRecord.setDeletedAt(dto.deletedAt());

        if (dto.studentId() != null) {
            Student student = new Student();
            student.setId(dto.studentId());
            educationRecord.setStudent(student);
        }

        if (dto.academicYearId() != null) {
            AcademicYear academicYear = new AcademicYear();
            academicYear.setId(dto.academicYearId());
            educationRecord.setAcademicYear(academicYear);
        }

        educationRecord.setFinalAverage(dto.finalAverage());
        educationRecord.setAbsenceDays(dto.absenceDays());
        educationRecord.setPassed(dto.passed());
        educationRecord.setNotes(dto.notes());

        return educationRecord;
    }
    public static void updateEntity(EducationRecord educationRecord, EducationRecordDto dto) {
        educationRecord.setFinalAverage(dto.finalAverage());
        educationRecord.setPassed(dto.passed());
        educationRecord.setAbsenceDays(dto.absenceDays());
        educationRecord.setNotes(dto.notes());
    }
}