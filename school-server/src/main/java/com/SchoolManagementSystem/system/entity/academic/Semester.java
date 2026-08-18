package com.SchoolManagementSystem.system.entity.academic;

import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.system.entity.school.AcademicYear;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "semesters", uniqueConstraints = {@UniqueConstraint(columnNames = {"academic_year_id", "semester_name"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Semester extends SchoolEntity {
    @ManyToOne
    @JoinColumn(name = "academic_year_id")
    private AcademicYear academicYear;

    @Column(name = "semester_name")
    @Enumerated(EnumType.STRING)
    private SemesterName semesterName;

    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

}