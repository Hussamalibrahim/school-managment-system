package com.SchoolManagementSystem.system.entity.academic;

import com.SchoolManagementSystem.system.entity.SchoolEntity;
import com.SchoolManagementSystem.system.entity.school.AcademicYear;
import com.SchoolManagementSystem.system.entity.student.Student;
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
public class EducationRecord extends SchoolEntity
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