package com.SchoolManagementSystem.System.mapper.user;

import com.SchoolManagementSystem.System.dto.user.LibrarianDto;
import com.SchoolManagementSystem.System.entity.user.Librarian;

public final class LibrarianMapper {

     private LibrarianMapper() {
     }

     public static LibrarianDto toDto(Librarian librarian) {
          return new LibrarianDto(
                  librarian.getId(),
                  librarian.getNationalId(),
                  librarian.getFirstName(),
                  librarian.getLastName(),
                  librarian.getPhone(),
                  librarian.getEmail(),
                  librarian.getAddress(),
                  librarian.getStatus(),
                  librarian.getHireDate(),
                  librarian.getCreatedAt(),
                  librarian.getUpdatedAt(),
                  librarian.getDeletedAt()
          );
     }

     public static Librarian toEntity(LibrarianDto dto) {
          Librarian librarian = new Librarian();

          librarian.setNationalId(dto.nationalId());
          librarian.setFirstName(dto.firstName());
          librarian.setLastName(dto.lastName());
          librarian.setPhone(dto.phone());
          librarian.setEmail(dto.email());
          librarian.setAddress(dto.address());
          librarian.setStatus(dto.status());
          librarian.setHireDate(dto.hireDate());

          return librarian;
     }
}