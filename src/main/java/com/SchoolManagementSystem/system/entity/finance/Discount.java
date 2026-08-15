package com.SchoolManagementSystem.System.entity.finance;

import com.SchoolManagementSystem.System.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "discounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discount extends BaseEntity
{
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "percentage")
    private Double percentage;

    @Column(name = "reason")
    private String reason;
}