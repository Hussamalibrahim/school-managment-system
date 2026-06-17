package com.SchoolManagementSystem.System.dtoMapper.academic;


import com.SchoolManagementSystem.System.dto.academic.SubjectDto;
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
                subject.getDescription()
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
        subject.setDescription(dto.description());

        return subject;
    }
}