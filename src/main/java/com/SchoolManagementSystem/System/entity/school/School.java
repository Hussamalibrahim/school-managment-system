package com.SchoolManagementSystem.System.entity.school;

import com.SchoolManagementSystem.System.entity.BaseEntity;
import com.SchoolManagementSystem.System.entity.enumeration.EducationStage;
import com.SchoolManagementSystem.System.entity.enumeration.SchoolCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "schools")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class School extends BaseEntity
{
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "logo_path")
    private String logoPath;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "supported_stages")
    private Set<EducationStage> supportedStages;

    @Enumerated(EnumType.STRING)
    @Column(name = "school_category")
    private SchoolCategory schoolCategory;
}