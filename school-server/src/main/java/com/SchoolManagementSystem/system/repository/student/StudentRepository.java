package com.SchoolManagementSystem.system.repository.student;

import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.enumeration.WarningReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.SchoolManagementSystem.system.entity.student.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRegistrationNumber(String registrationNumber);

    List<Student> findByStudentSchoolClass_Id(Long classId);

    List<Student> findByStudentSchoolClass_IdIn(Set<Long> classIds);

    void deleteById(Student student);

    boolean existsByRegistrationNumberAndIdNot(String s, Long id);

    @Query("""
                SELECT s
                FROM Student s
                JOIN Warning w ON w.student = s
                WHERE (:reason IS NULL OR w.reason = :reason)
                GROUP BY s
                HAVING COUNT(w) = :count
            """)
    List<Student> findStudentsByWarningStatistics(
            @Param("reason") WarningReason reason,
            @Param("count") Long count);

    @Query("""
                SELECT DISTINCT s
                FROM Student s
                JOIN Warning w ON w.student = s
                WHERE (:reason IS NULL OR w.reason = :reason)
            """)
    List<Student> findStudentsByWarningReason(
            @Param("reason") WarningReason reason
    );

    long countBySchoolId(Long schoolId);

    List<Student> findByGradeLevel(GradeLevel gradeLevel);
}