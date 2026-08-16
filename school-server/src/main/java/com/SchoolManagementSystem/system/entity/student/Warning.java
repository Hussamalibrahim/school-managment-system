package com.SchoolManagementSystem.system.entity.student;

import com.SchoolManagementSystem.system.entity.SchoolEntity;
import com.SchoolManagementSystem.system.entity.enumeration.WarningReason;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "warnings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Warning extends SchoolEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false)
    private WarningReason reason;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "warning_date", nullable = false)
    private LocalDate warningDate;
}