package com.SchoolManagementSystem.system.mapper.finance;
import com.SchoolManagementSystem.system.dto.finance.FeeTypeDto;
import com.SchoolManagementSystem.system.entity.finance.FeeType;

public final class FeeTypeMapper {
    private FeeTypeMapper() {}
    public static FeeTypeDto toDto(FeeType feeType) {
        if (feeType == null) return null;

        return new FeeTypeDto(
                feeType.getId(),
                feeType.getCreatedAt(),
                feeType.getUpdatedAt(),
                feeType.getDeletedAt(),
                feeType.getName()
        );
    }

    public static FeeType toEntity(FeeTypeDto dto) {
        if (dto == null) return null;

        FeeType feeType = new FeeType();
        feeType.setId(dto.id());
        feeType.setCreatedAt(dto.createdAt());
        feeType.setUpdatedAt(dto.updatedAt());
        feeType.setDeletedAt(dto.deletedAt());
        feeType.setName(dto.name());

        return feeType;
    }
}