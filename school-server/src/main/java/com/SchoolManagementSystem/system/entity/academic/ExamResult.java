package com.SchoolManagementSystem.system.entity.academic;

import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import com.SchoolManagementSystem.system.entity.student.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "exam_results",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "exam_id",
                        "student_id"
                })
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamResult extends SchoolEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(nullable = false,name = "score")
    @Positive
    private Double score;
}