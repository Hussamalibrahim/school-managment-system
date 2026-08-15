package com.SchoolManagementSystem.system.mapper.library;

import com.SchoolManagementSystem.system.dto.library.LibraryBookDto;
import com.SchoolManagementSystem.system.entity.library.LibraryBook;

public final class LibraryBookMapper {

     private LibraryBookMapper() {}

     public static LibraryBookDto toDto(LibraryBook book) {
          if (book == null) return null;

          return new LibraryBookDto(
                  book.getId(),
                  book.getCreatedAt(),
                  book.getUpdatedAt(),
                  book.getDeletedAt(),
                  book.getTitle(),
                  book.getAuthor(),
                  book.getIsbn(),
                  book.getCategory(),
                  book.getDescription()
          );
     }

     public static LibraryBook toEntity(LibraryBookDto dto) {
          if (dto == null) return null;

          LibraryBook book = new LibraryBook();

          book.setId(dto.id());
          book.setCreatedAt(dto.createdAt());
          book.setUpdatedAt(dto.updatedAt());
          book.setDeletedAt(dto.deletedAt());
          book.setTitle(dto.title());
          book.setAuthor(dto.author());
          book.setIsbn(dto.isbn());
          book.setCategory(dto.category());
          book.setDescription(dto.description());

          return book;
     }
}