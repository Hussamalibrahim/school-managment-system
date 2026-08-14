package com.SchoolManagementSystem.System.repository.student;

import com.SchoolManagementSystem.System.dto.student.AttendanceDto;
import com.SchoolManagementSystem.System.entity.enumeration.AttendanceStatus;
import com.SchoolManagementSystem.System.entity.student.Attendance;
import com.SchoolManagementSystem.System.entity.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    boolean existsByStudentIdAndAttendanceDate(Long studentId, LocalDate attendanceDate);

    List<Attendance> findByStudentIdOrderByAttendanceDateDesc(Long studentId);

    List<Attendance> findByStudentIdAndAttendanceDateBetweenOrderByAttendanceDateDesc(
            Long studentId, LocalDate from, LocalDate to);

    Attendance findByStudentIdAndAttendanceDate(Long aLong, LocalDate localDate);

    long countByStudentIdAndAttendanceStatus(Long studentId, AttendanceStatus status);

    long countByAttendanceStatus(AttendanceStatus status);

    List<Attendance> findByStudentIdInOrderByAttendanceDateDesc(List<Long> studentIds);

    @Query("""
            SELECT a.student
            FROM Attendance a
            WHERE a.attendanceStatus = :status
            GROUP BY a.student
            HAVING COUNT(a) >= :limit
            """)
    List<Student> findStudentsExceeded(AttendanceStatus status, long limit);
}