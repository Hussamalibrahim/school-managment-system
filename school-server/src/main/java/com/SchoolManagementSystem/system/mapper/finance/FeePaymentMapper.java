package com.SchoolManagementSystem.system.mapper.finance;

import com.SchoolManagementSystem.system.dto.finance.response.FeePaymentDto;
import com.SchoolManagementSystem.system.entity.enumeration.DiscountType;
import com.SchoolManagementSystem.system.entity.finance.Discount;
import com.SchoolManagementSystem.system.entity.finance.Fee;
import com.SchoolManagementSystem.system.entity.finance.FeePayment;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class FeePaymentMapper {

    private FeePaymentMapper() {
    }

    public static FeePaymentDto toDto(FeePayment payment, BigDecimal paidAmount) {

        Fee fee = payment.getFee();

        BigDecimal requiredAmount = calculateRequiredAmount(fee);

        BigDecimal remainingAmount = requiredAmount.subtract(paidAmount);

        return new FeePaymentDto(
                payment.getId(),
                fee.getId(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getPaymentMethod(),
                payment.getReceiptNumber(),
                requiredAmount,
                paidAmount,
                remainingAmount
        );
    }

    public static BigDecimal calculateRequiredAmount(Fee fee) {

        BigDecimal amount = fee.getAmount();

        Discount discount = fee.getDiscount();

        if (discount == null) {
            return amount;
        }

        if (discount.getDiscountType() == DiscountType.PERCENTAGE) {
            BigDecimal discountAmount = amount.multiply(discount.getValue())
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            return amount.subtract(discountAmount);
        }

        return amount.subtract(discount.getValue());
    }
}