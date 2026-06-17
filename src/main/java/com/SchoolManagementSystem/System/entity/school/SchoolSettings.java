package com.SchoolManagementSystem.System.entity.school;

import com.SchoolManagementSystem.System.entity.BaseEntity;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "school_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolSettings extends BaseEntity
{
    @OneToOne
    @JoinColumn(name = "school_id")
    private School school;

    @Column(name = "school_start_time")
    private LocalTime schoolStartTime;

    @Column(name = "period_duration_minutes")
    private Integer periodDurationMinutes;

    @Column(name = "break_duration_minutes")
    private Integer breakDurationMinutes;

    @Column(name = "max_allowed_absences")
    private Integer maxAllowedAbsences;

}
