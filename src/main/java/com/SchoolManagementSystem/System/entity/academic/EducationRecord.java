package com.SchoolManagementSystem.System.entity.academic;

import com.SchoolManagementSystem.System.entity.BaseEntity;
import com.SchoolManagementSystem.System.entity.school.AcademicYear;
import com.SchoolManagementSystem.System.entity.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "education_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EducationRecord extends BaseEntity
{

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "academic_year_id")
    private AcademicYear academicYear;

    @Column(name = "final_average")
    private Double finalAverage;

    @Column(name = "absence_days")
    private Integer absenceDays;

    @Column(name = "passed")
    private Boolean passed;

    @Column(name = "notes")
    private String notes;
}