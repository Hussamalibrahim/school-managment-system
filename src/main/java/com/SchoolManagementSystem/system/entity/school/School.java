package com.SchoolManagementSystem.System.entity.school;

import com.SchoolManagementSystem.System.entity.BaseEntity;
import com.SchoolManagementSystem.System.entity.enumeration.EducationStage;
import com.SchoolManagementSystem.System.entity.enumeration.SchoolType;
import com.SchoolManagementSystem.System.entity.enumeration.SemesterName;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(
        name = "schools",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_school_code", columnNames = "code")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class School extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false, unique = true, length = 100)
    private String code;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "logo_path")
    private String logoPath;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "school_education_stages",
            joinColumns = @JoinColumn(name = "school_id")
    )
    @Column(name = "education_stage")
    @Enumerated(EnumType.STRING)
    private Set<EducationStage> educationStages;

    @Enumerated(EnumType.STRING)
    @Column(name = "school_type")
    private SchoolType schoolType;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester_name")
    private SemesterName semesterName;
}