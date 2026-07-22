package com.SchoolManagementSystem.System.mapper.academic;


import com.SchoolManagementSystem.System.dto.academic.SubjectDto;
import com.SchoolManagementSystem.System.dto.academic.request.SubjectCreateRequest;
import com.SchoolManagementSystem.System.dto.academic.request.SubjectNameDto;
import com.SchoolManagementSystem.System.entity.academic.Subject;

public final class SubjectMapper {
    private SubjectMapper(){}
    public static SubjectDto toDto(Subject subject) {
        if (subject == null) return null;

        return new SubjectDto(
                subject.getId(),
                subject.getCreatedAt(),
                subject.getUpdatedAt(),
                subject.getDeletedAt(),
                subject.getName(),
                subject.getGradeLevel(),
                subject.getSemesterName()
        );
    }

    public static Subject toEntity(SubjectDto dto) {
        if (dto == null) return null;

        Subject subject = new Subject();
        subject.setId(dto.id());
        subject.setCreatedAt(dto.createdAt());
        subject.setUpdatedAt(dto.updatedAt());
        subject.setDeletedAt(dto.deletedAt());
        subject.setName(dto.name());
        subject.setGradeLevel(dto.gradeLevel());
        subject.setSemesterName(dto.semesterName());

        return subject;
    }

    public static SubjectNameDto toNameDto(Subject subject) {
        if (subject == null) return null;

        return new SubjectNameDto(
                subject.getId(),
                subject.getName()
        );
    }
    public static Subject fromCreateRequest(
            SubjectCreateRequest request) {

        Subject subject = new Subject();

        subject.setName(request.name());
        subject.setGradeLevel(request.gradeLevel());
        subject.setSemesterName(request.semesterName());

        return subject;
    }

    public static void updateEntity(Subject subject, SubjectDto dto) {

        subject.setName(dto.name());
        subject.setGradeLevel(dto.gradeLevel());
        subject.setSemesterName(dto.semesterName());
    }
}