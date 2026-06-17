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

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "overall_result")
    private String overallResult;

    @Column(name = "notes")
    private String notes;
}