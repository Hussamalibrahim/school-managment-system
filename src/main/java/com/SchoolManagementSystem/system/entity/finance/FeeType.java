package com.SchoolManagementSystem.System.entity.finance;

import com.SchoolManagementSystem.System.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fee_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeeType extends BaseEntity
{
    @Column(nullable = false)
    private String name;
}