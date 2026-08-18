package com.SchoolManagementSystem.system.entity.academic;

import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(
        name = "school_class",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "school_id",
                                "grade_level",
                                "section"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClass extends SchoolEntity
{
    @OneToMany(mappedBy = "studentSchoolClass")
    private List<Student> students;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade_level")
    private GradeLevel gradeLevel;

    @Column(name = "section")
    private String section;

    @Column(name = "location")
    private String location;

    @Column(name = "capacity")
    private Integer capacity;

}
