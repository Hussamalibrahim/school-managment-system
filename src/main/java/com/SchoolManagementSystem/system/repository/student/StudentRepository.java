package com.SchoolManagementSystem.System.repository.student;

import com.SchoolManagementSystem.System.dto.student.StudentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.SchoolManagementSystem.System.entity.student.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long>
{
    Optional<Student> findByRegistrationNumber(String registrationNumber);



    List<Student> findByStudentSchoolClass_Id(Long classId);

    List<Student> findByStudentSchoolClass_IdIn(Set<Long> classIds);

    void deleteById(Student student);

    boolean existsByRegistrationNumberAndIdNot(String s, Long id);
}