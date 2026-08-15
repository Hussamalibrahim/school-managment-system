package com.SchoolManagementSystem.system.entity.library;

import com.SchoolManagementSystem.system.entity.BaseEntity;
import com.SchoolManagementSystem.system.entity.school.School;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "libraries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Library extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "school_id", unique = true, nullable = false)
    private School school;
}