package com.SchoolManagementSystem.system.repository.academic;

import com.SchoolManagementSystem.system.entity.academic.Semester;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long>
{
    void deleteById(Semester semester);


    boolean existsBySemesterName(SemesterName semesterName);

    Optional<Semester> findBySemesterName(SemesterName semesterName);

    @Query("""
    SELECT s
    FROM Semester s
    WHERE s.academicYear.id = :academicYearId
      AND :date BETWEEN s.startDate AND s.endDate
""")
    Optional<Semester> findCurrentSemester(@Param("academicYearId") Long academicYearId, @Param("date") LocalDate date);

    Optional<Semester> findByAcademicYearIdAndSemesterName(Long academicYearId, SemesterName semesterName);

    Optional<Semester> findByAcademicYearIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Long academicYearId, LocalDate date1, LocalDate date2);
}