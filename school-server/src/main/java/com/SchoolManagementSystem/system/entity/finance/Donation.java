package com.SchoolManagementSystem.system.entity.finance;

import com.SchoolManagementSystem.system.entity.enumeration.DonationStatus;
import com.SchoolManagementSystem.system.entity.enumeration.PaymentMethod;
import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import com.SchoolManagementSystem.system.entity.user.Guardian;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Donation extends SchoolEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "campaign_id", nullable = false)
    private DonationCampaign campaign;

    @ManyToOne(optional = false)
    @JoinColumn(name = "guardian_id", nullable = false)
    private Guardian guardian;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DonationStatus status;

    @Column(name = "donation_date", nullable = false)
    private LocalDateTime donationDate;

    @Column(name = "receipt_number", unique = true)
    private String receiptNumber;

    @Column(columnDefinition = "TEXT")
    private String notes;
}