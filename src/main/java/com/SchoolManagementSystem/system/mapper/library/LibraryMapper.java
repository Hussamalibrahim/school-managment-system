package com.SchoolManagementSystem.System.mapper.library;

import com.SchoolManagementSystem.System.dto.library.LibraryDto;
import com.SchoolManagementSystem.System.entity.library.Library;

public final class LibraryMapper {

    private LibraryMapper() {}

    public static LibraryDto toDto(Library library) {
        if (library == null) return null;

        return new LibraryDto(
                library.getId(),
                library.getCreatedAt(),
                library.getUpdatedAt(),
                library.getDeletedAt()
        );
    }

    public static Library toEntity(LibraryDto dto) {
        if (dto == null) return null;

        Library library = new Library();

        library.setId(dto.id());
        library.setCreatedAt(dto.createdAt());
        library.setUpdatedAt(dto.updatedAt());
        library.setDeletedAt(dto.deletedAt());

        return library;
    }
}