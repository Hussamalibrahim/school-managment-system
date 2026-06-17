package com.SchoolManagementSystem.System.entity.school;

import com.SchoolManagementSystem.System.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "school_type")
    private String schoolType;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "logo_path")
    private String logoPath;

    @Column(name = "supports_primary")
    private Boolean supportsPrimary;

    @Column(name = "supports_middle")
    private Boolean supportsMiddle;

    @Column(name = "supports_secondary")
    private Boolean supportsSecondary;

}