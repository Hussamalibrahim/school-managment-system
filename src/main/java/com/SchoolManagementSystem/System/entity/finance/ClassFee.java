package com.SchoolManagementSystem.System.entity.finance;

import com.SchoolManagementSystem.System.entity.BaseEntity;
import com.SchoolManagementSystem.System.entity.academic.SchoolClass;
import com.SchoolManagementSystem.System.entity.school.AcademicYear;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "class_fees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassFee extends BaseEntity
{
    @ManyToOne
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    @ManyToOne
    @JoinColumn(name = "academic_year_id")
    private AcademicYear academicYear;

    @ManyToOne
    @JoinColumn(name = "fee_type")
    private FeeType feeType;

    @Column(name = "amount", nullable = false)
    private Double amount;
}