package com.SchoolManagementSystem.System.repository.academic;

import com.SchoolManagementSystem.System.entity.academic.Semester;
import com.SchoolManagementSystem.System.entity.enumeration.SemesterName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long>
{
    void deleteById(Semester semester);


    boolean existsBySemesterName(SemesterName semesterName);
}