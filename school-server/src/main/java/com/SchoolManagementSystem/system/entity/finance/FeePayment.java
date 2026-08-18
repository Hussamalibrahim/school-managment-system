package com.SchoolManagementSystem.system.entity.finance;

import com.SchoolManagementSystem.system.entity.enumeration.PaymentMethod;
import jakarta.persistence.*;
import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fee_payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeePayment extends SchoolEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "fee_id")
    private Fee fee;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "receipt_number", unique = true)
    private String receiptNumber;

}