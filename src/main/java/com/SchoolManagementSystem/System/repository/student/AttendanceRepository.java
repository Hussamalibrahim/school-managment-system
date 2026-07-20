package com.SchoolManagementSystem.System.repository.student;

import com.SchoolManagementSystem.System.entity.student.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    boolean existsByStudentIdAndAttendanceDate(Long studentId, LocalDate attendanceDate);

    List<Attendance> findByStudentIdOrderByAttendanceDateDesc(Long studentId);
    List<Attendance> findByStudentIdAndAttendanceDateBetweenOrderByAttendanceDateDesc(
            Long studentId, LocalDate from, LocalDate to);

    Attendance findByStudentIdAndAttendanceDate(Long aLong, LocalDate localDate);
}