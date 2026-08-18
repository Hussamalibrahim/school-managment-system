package com.SchoolManagementSystem.system.entity.academic;

import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import com.SchoolManagementSystem.system.entity.enumeration.ExamCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "exam")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exam extends SchoolEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(optional = false)
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "category")
    private ExamCategory category;

    @Column(nullable = false, name = "max_score")
    private Double maxScore;

    @Column(nullable = false, name = "weight")
    private Double weight;

    @Column(nullable = false, name = "exam_datetime")
    private LocalDateTime examDateTime;

    @Column(nullable = false, name = "duration_minutes")
    private Integer durationMinutes;
}