package com.SchoolManagementSystem.system.entity.academic;

import com.SchoolManagementSystem.system.entity.SchoolEntity;
import com.SchoolManagementSystem.system.entity.enumeration.PeriodNumber;
import com.SchoolManagementSystem.system.entity.user.Teacher;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;

@Entity
@Table(name = "class_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassSchedule extends SchoolEntity {

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