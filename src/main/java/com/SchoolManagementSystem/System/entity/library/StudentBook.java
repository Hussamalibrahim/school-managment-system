package com.SchoolManagementSystem.System.entity.library;
import com.SchoolManagementSystem.System.entity.BaseEntity;
import com.SchoolManagementSystem.System.entity.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "student_books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentBook extends BaseEntity
{
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "school_book_id")
    private SchoolBook schoolBook;

    @Column(name = "given_date")
    private LocalDate givenDate;
}