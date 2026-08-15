package com.SchoolManagementSystem.system.mapper.library;

import com.SchoolManagementSystem.system.dto.library.BorrowDto;
import com.SchoolManagementSystem.system.entity.library.Borrow;

public final class BorrowMapper {

    private BorrowMapper() {}

    public static BorrowDto toDto(Borrow borrow) {
        if (borrow == null) return null;

        return new BorrowDto(
                borrow.getId(),
                borrow.getCreatedAt(),
                borrow.getUpdatedAt(),
                borrow.getDeletedAt(),
                borrow.getBorrowDate(),
                borrow.getDueDate(),
                borrow.getReturnDate()
        );
    }

    public static Borrow toEntity(BorrowDto dto) {
        if (dto == null) return null;

        Borrow borrow = new Borrow();

        borrow.setId(dto.id());
        borrow.setCreatedAt(dto.createdAt());
        borrow.setUpdatedAt(dto.updatedAt());
        borrow.setDeletedAt(dto.deletedAt());
        borrow.setBorrowDate(dto.borrowDate());
        borrow.setDueDate(dto.dueDate());
        borrow.setReturnDate(dto.returnDate());

        return borrow;
    }
}