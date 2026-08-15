package com.SchoolManagementSystem.System.entity.library;

import com.SchoolManagementSystem.System.entity.BaseEntity;
import com.SchoolManagementSystem.System.entity.school.School;
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