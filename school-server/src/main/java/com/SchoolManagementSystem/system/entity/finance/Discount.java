package com.SchoolManagementSystem.system.entity.finance;

import com.SchoolManagementSystem.system.entity.BaseEntity;
import com.SchoolManagementSystem.system.entity.enumeration.DiscountType;
import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "discounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discount extends SchoolEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "value", nullable = false, precision = 12, scale = 2)
    private BigDecimal value;

    @Column(name = "reason")
    private String reason;

    @OneToOne(optional = false)
    @JoinColumn(name = "fee_id", nullable = false, unique = true)
    private Fee fee;
}
