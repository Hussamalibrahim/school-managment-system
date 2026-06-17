package com.SchoolManagementSystem.System.dtoMapper.finance;
import com.SchoolManagementSystem.System.dto.finance.StudentDiscountDto;
import com.SchoolManagementSystem.System.entity.finance.StudentDiscount;

public final class StudentDiscountMapper {
     private StudentDiscountMapper() {}
     public static StudentDiscountDto toDto(StudentDiscount studentDiscount) {
          if (studentDiscount == null) return null;

          return new StudentDiscountDto(
                  studentDiscount.getId(),
                  studentDiscount.getCreatedAt(),
                  studentDiscount.getUpdatedAt(),
                  studentDiscount.getDeletedAt()
          );
     }

     public static StudentDiscount toEntity(StudentDiscountDto dto) {
          if (dto == null) return null;

          StudentDiscount studentDiscount = new StudentDiscount();
          studentDiscount.setId(dto.id());
          studentDiscount.setCreatedAt(dto.createdAt());
          studentDiscount.setUpdatedAt(dto.updatedAt());
          studentDiscount.setDeletedAt(dto.deletedAt());

          return studentDiscount;
     }
}