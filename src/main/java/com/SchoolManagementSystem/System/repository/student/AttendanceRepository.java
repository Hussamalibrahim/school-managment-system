package com.SchoolManagementSystem.System.repository.student;

import com.SchoolManagementSystem.System.entity.student.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>
{
}