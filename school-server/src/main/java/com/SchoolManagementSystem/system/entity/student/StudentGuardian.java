package com.SchoolManagementSystem.system.entity.student;

import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import com.SchoolManagementSystem.system.entity.user.Guardian;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student_guardians")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentGuardian extends SchoolEntity
{
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "guardian_id")
    private Guardian guardian;

    @Column(name = "primary_guardian")
    private Boolean primaryGuardian;
}
