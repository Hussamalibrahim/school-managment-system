package com.SchoolManagementSystem.System.entity.academic;

import com.SchoolManagementSystem.System.entity.BaseEntity;
import com.SchoolManagementSystem.System.entity.enumeration.PeriodNumber;
import com.SchoolManagementSystem.System.entity.user.Teacher;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;

@Entity
@Table(name = "class_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassSchedule extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "period_number")
    @Enumerated(EnumType.STRING)
    private PeriodNumber periodNumber;
}