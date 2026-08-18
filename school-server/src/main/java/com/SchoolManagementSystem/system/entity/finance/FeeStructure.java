package com.SchoolManagementSystem.system.entity.finance;

import com.SchoolManagementSystem.system.entity.academic.Semester;
import com.SchoolManagementSystem.system.entity.enumeration.FeeType;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "fee_structures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeeStructure extends SchoolEntity {


    @Column(name = "fee_name", nullable = false)
    private String feeName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade_level", nullable = false)
    private GradeLevel gradeLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "fee_type", nullable = false)
    private FeeType feeType;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name =  "active", nullable = false)
    private Boolean active = true;
}