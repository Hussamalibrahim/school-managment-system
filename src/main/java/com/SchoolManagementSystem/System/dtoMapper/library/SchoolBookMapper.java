package com.SchoolManagementSystem.System.dtoMapper.library;

import com.SchoolManagementSystem.System.dto.library.SchoolBookDto;
import com.SchoolManagementSystem.System.entity.library.SchoolBook;

public final class SchoolBookMapper {

    private SchoolBookMapper() {}

    public static SchoolBookDto toDto(SchoolBook book) {
        if (book == null) return null;

        return new SchoolBookDto(
                book.getId(),
                book.getCreatedAt(),
                book.getUpdatedAt(),
                book.getDeletedAt(),
                book.getName()
        );
    }

    public static SchoolBook toEntity(SchoolBookDto dto) {
        if (dto == null) return null;

        SchoolBook book = new SchoolBook();

        book.setId(dto.id());
        book.setCreatedAt(dto.createdAt());
        book.setUpdatedAt(dto.updatedAt());
        book.setDeletedAt(dto.deletedAt());
        book.setName(dto.name());

        return book;
    }
}