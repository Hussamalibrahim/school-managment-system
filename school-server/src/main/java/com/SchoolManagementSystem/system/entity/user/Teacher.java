package com.SchoolManagementSystem.system.entity.user;

import com.SchoolManagementSystem.system.entity.academic.TeacherSubject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends BaseUser
{
    @OneToMany(mappedBy = "teacher")
    private List<TeacherSubject> teacherSubjects;

    private String specialization;

}