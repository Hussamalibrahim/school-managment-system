package com.SchoolManagementSystem.System.dtoMapper.finance;

import com.SchoolManagementSystem.System.dto.finance.PaymentDto;
import com.SchoolManagementSystem.System.entity.finance.Payment;
import org.mapstruct.Mapper;

public final class PaymentMapper {
    private PaymentMapper() {}
    public static PaymentDto toDto(Payment payment) {
        if (payment == null) return null;

        return new PaymentDto(
                payment.getId(),
                payment.getCreatedAt(),
                payment.getUpdatedAt(),
                payment.getDeletedAt(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getNotes()
        );
    }

    public static Payment toEntity(PaymentDto dto) {
        if (dto == null) return null;

        Payment payment = new Payment();
        payment.setId(dto.id());
        payment.setCreatedAt(dto.createdAt());
        payment.setUpdatedAt(dto.updatedAt());
        payment.setDeletedAt(dto.deletedAt());
        payment.setAmount(dto.amount());
        payment.setPaymentDate(dto.paymentDate());
        payment.setNotes(dto.notes());

        return payment;
    }
}