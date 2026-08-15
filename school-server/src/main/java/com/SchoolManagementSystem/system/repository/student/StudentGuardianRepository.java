package com.SchoolManagementSystem.system.repository.student;

import com.SchoolManagementSystem.system.entity.student.Student;
import com.SchoolManagementSystem.system.entity.student.StudentGuardian;
import com.SchoolManagementSystem.system.entity.user.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentGuardianRepository extends JpaRepository<StudentGuardian, Long> {
    boolean existsByStudentIdAndGuardianId(Long studentId, Long guardianId);

    List<StudentGuardian> findByStudentId(Long studentId);

    List<StudentGuardian> findByGuardianId(Long guardianId);

    Optional<StudentGuardian> findByStudentIdAndGuardianId(Long studentId, Long guardianId);
    @Query("""
       SELECT g
       FROM Guardian g
       WHERE NOT EXISTS (
           SELECT sg
           FROM StudentGuardian sg
           WHERE sg.guardian = g
       )
       """)
    List<Guardian> findGuardiansWithoutStudents();

    @Query("""
       SELECT s
       FROM Student s
       WHERE NOT EXISTS (
           SELECT sg
           FROM StudentGuardian sg
           WHERE sg.student = s
       )
       """)
    List<Student> findStudentsWithoutGuardian();
}