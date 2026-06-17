package com.SchoolManagementSystem.System.dtoMapper.finance;

import com.SchoolManagementSystem.System.entity.finance.ClassFee;
import com.SchoolManagementSystem.System.dto.finance.ClassFeeDto;

public final class ClassFeeMapper {

    private ClassFeeMapper() {}

    public static ClassFeeDto toDto(ClassFee classFee) {
        if (classFee == null) return null;

        return new ClassFeeDto(
                classFee.getId(),
                classFee.getCreatedAt(),
                classFee.getUpdatedAt(),
                classFee.getDeletedAt(),
                classFee.getAmount()
        );
    }

    public static ClassFee toEntity(ClassFeeDto dto) {
        if (dto == null) return null;

        ClassFee classFee = new ClassFee();
        classFee.setId(dto.id());
        classFee.setCreatedAt(dto.createdAt());
        classFee.setUpdatedAt(dto.updatedAt());
        classFee.setDeletedAt(dto.deletedAt());
        classFee.setAmount(dto.amount());

        return classFee;
    }
}