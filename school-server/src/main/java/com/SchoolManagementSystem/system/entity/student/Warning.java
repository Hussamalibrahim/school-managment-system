package com.SchoolManagementSystem.system.entity.student;
import com.SchoolManagementSystem.system.entity.SchoolEntity;
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
public class Warning extends SchoolEntity
{
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private LocalDate warningDate;

    private String reason;

}
