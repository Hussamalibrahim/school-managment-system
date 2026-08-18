package com.SchoolManagementSystem.system.mapper.finance;

import com.SchoolManagementSystem.system.dto.finance.DiscountDto;
import com.SchoolManagementSystem.system.dto.finance.request.DiscountRequest;
import com.SchoolManagementSystem.system.entity.finance.Discount;
import com.SchoolManagementSystem.system.entity.finance.Fee;

public final class DiscountMapper {

    private DiscountMapper() {
    }

    public static Discount toEntity(
            DiscountRequest request,
            Fee fee) {

        Discount discount = new Discount();

        discount.setName(request.name());
        discount.setDiscountType(request.discountType());
        discount.setValue(request.value());
        discount.setReason(request.reason());
        discount.setFee(fee);

        return discount;
    }

    public static DiscountDto toDto(
            Discount discount) {

        return new DiscountDto(
                discount.getId(),
                discount.getFee().getId(),
                discount.getName(),
                discount.getDiscountType(),
                discount.getValue(),
                discount.getReason()
        );
    }
}