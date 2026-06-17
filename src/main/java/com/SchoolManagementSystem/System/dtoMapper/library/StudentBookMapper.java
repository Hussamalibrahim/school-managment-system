package com.SchoolManagementSystem.System.dtoMapper.library;

import com.SchoolManagementSystem.System.dto.library.StudentBookDto;
import com.SchoolManagementSystem.System.entity.library.StudentBook;

public final class StudentBookMapper {

    private StudentBookMapper() {}

    public static StudentBookDto toDto(StudentBook book) {
        if (book == null) return null;

        return new StudentBookDto(
                book.getId(),
                book.getCreatedAt(),
                book.getUpdatedAt(),
                book.getDeletedAt(),
                book.getGivenDate()
        );
    }

    public static StudentBook toEntity(StudentBookDto dto) {
        if (dto == null) return null;

        StudentBook book = new StudentBook();

        book.setId(dto.id());
        book.setCreatedAt(dto.createdAt());
        book.setUpdatedAt(dto.updatedAt());
        book.setDeletedAt(dto.deletedAt());
        book.setGivenDate(dto.givenDate());

        return book;
    }
}