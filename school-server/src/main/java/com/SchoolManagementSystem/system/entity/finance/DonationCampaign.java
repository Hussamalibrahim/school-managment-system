package com.SchoolManagementSystem.system.entity.finance;

import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "donation_campaigns")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonationCampaign extends SchoolEntity {

    @Column(nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "target_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal targetAmount;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "active", nullable = false)
    private Boolean active = true;
}