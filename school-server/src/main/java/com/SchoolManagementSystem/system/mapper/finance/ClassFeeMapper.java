package com.SchoolManagementSystem.system.mapper.finance;

import com.SchoolManagementSystem.system.entity.finance.ClassFee;
import com.SchoolManagementSystem.system.dto.finance.ClassFeeDto;

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