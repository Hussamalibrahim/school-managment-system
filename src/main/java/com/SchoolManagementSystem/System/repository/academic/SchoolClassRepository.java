package com.SchoolManagementSystem.System.repository.academic;

import com.SchoolManagementSystem.System.entity.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SchoolManagementSystem.System.entity.academic.SchoolClass;

import java.util.List;


@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
}