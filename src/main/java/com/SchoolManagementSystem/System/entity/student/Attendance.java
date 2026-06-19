package com.SchoolManagementSystem.System.entity.student;

import com.SchoolManagementSystem.System.entity.BaseEntity;
import com.SchoolManagementSystem.System.entity.enumeration.AttendanceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "attendance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attendance extends BaseEntity
{

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "attendance_date")
    private LocalDate attendanceDate;

    @Column(name = "attendance_Status")
    private AttendanceStatus attendanceStatus;

}