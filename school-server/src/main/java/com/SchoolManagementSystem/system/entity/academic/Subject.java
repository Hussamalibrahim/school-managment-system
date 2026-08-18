package com.SchoolManagementSystem.system.entity.academic;

import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "subjects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subject extends SchoolEntity
{
    @OneToMany(mappedBy = "subject")
    private List<TeacherSubject> teacherSubjects;

    @Column(name = "name",nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade_level", nullable = false)
    private GradeLevel gradeLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester_name", nullable = false)
    private SemesterName semesterName;

}