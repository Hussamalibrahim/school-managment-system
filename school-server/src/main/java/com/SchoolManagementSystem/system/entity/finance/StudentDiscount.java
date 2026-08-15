package com.SchoolManagementSystem.system.entity.finance;

import com.SchoolManagementSystem.system.entity.BaseEntity;
import com.SchoolManagementSystem.system.entity.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student_discounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDiscount extends BaseEntity
{
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;
}
