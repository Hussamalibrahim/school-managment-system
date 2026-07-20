package com.SchoolManagementSystem.System.repository.student;

import com.SchoolManagementSystem.System.dto.student.StudentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import com.SchoolManagementSystem.System.entity.student.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long>
{
    Optional<Student> findByRegistrationNumber(String registrationNumber);
    List<Student> findByStudentSchoolClassIdIn(List<Long> classIds);

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

    List<Student> findByStudentSchoolClass_Id(Long classId);
}