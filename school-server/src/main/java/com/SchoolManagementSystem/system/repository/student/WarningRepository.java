package com.SchoolManagementSystem.system.repository.student;

import com.SchoolManagementSystem.system.entity.enumeration.WarningReason;
import com.SchoolManagementSystem.system.entity.student.Warning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarningRepository extends JpaRepository<Warning, Long> {
    List<Warning> findByStudentId(Long studentId);

    @Query("""
        SELECT w
        FROM Warning w
        WHERE (:reason IS NULL OR w.reason = :reason)
    """)
    List<Warning> findByReason(@Param("reason") WarningReason reason);
}