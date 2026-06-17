package com.SchoolManagementSystem.System.entity.library;

import com.SchoolManagementSystem.System.entity.BaseEntity;
import com.SchoolManagementSystem.System.entity.academic.Subject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "school_books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolBook extends BaseEntity
{
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "name", nullable = false)
    private String name;
}