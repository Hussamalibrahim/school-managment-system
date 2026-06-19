package com.SchoolManagementSystem.System.mapper.finance;

import com.SchoolManagementSystem.System.dto.finance.DiscountDto;
import com.SchoolManagementSystem.System.entity.finance.Discount;

public final class DiscountMapper {
    private DiscountMapper() {}

    public static DiscountDto toDto(Discount discount) {
        if (discount == null) return null;

        return new DiscountDto(
                discount.getId(),
                discount.getCreatedAt(),
                discount.getUpdatedAt(),
                discount.getDeletedAt(),
                discount.getName(),
                discount.getPercentage(),
                discount.getReason()
        );
    }

    public static Discount toEntity(DiscountDto dto) {
        if (dto == null) return null;

        Discount discount = new Discount();
        discount.setId(dto.id());
        discount.setCreatedAt(dto.createdAt());
        discount.setUpdatedAt(dto.updatedAt());
        discount.setDeletedAt(dto.deletedAt());
        discount.setName(dto.name());
        discount.setPercentage(dto.percentage());
        discount.setReason(dto.reason());

        return discount;
    }
}