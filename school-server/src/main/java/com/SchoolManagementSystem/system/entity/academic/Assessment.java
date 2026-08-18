package com.SchoolManagementSystem.system.entity.academic;

import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import com.SchoolManagementSystem.system.entity.enumeration.ContinuousCategory;
import com.SchoolManagementSystem.system.entity.user.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "assessments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Assessment extends SchoolEntity
{

    @ManyToOne(optional = false)
    @JoinColumn(name = "class_schedule_id")
    private ClassSchedule classSchedule;

    @JoinColumn(name = "semester_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Semester semester;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ContinuousCategory category;

    @Column(name = "max_score")
    private Double maxScore;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "assessment_date")
    private LocalDate assessmentDate;
}
