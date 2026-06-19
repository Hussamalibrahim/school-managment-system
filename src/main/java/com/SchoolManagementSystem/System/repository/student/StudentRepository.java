package com.SchoolManagementSystem.System.repository.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import com.SchoolManagementSystem.System.entity.student.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long>
{
    Optional<Student> findByRegistrationNumber(String registrationNumber);
    List<Student> findByStudentSchoolClassIdIn(List<Long> classIds);

}