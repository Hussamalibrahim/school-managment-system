package com.SchoolManagementSystem.system.repository.academic;

import com.SchoolManagementSystem.system.entity.academic.Semester;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long>
{
    void deleteById(Semester semester);


    boolean existsBySemesterName(SemesterName semesterName);

    Optional<Semester> findBySemesterName(SemesterName semesterName);
}