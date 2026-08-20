package com.SchoolManagementSystem.system.entity.academic;

import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.school.AcademicYear;
import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import com.SchoolManagementSystem.system.entity.student.Student;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "education_records",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "student_id",
                                "academic_year_id"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EducationRecord extends SchoolEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "academic_year_id")
    private AcademicYear academicYear;

    /**
     * The class the student belonged to
     * during this academic year.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade_level", nullable = false)
    private GradeLevel gradeLevel;

    @Column(name = "final_average")
    private Double finalAverage;

    @Column(name = "absence_days")
    private Integer absenceDays;

    @Column(name = "passed")
    private Boolean passed;

    @Column(name = "registered_next_year")
    private Boolean registeredNextYear = false;

    @Column(name = "notes")
    private String notes;
}