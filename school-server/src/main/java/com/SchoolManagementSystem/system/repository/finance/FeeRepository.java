package com.SchoolManagementSystem.system.repository.finance;

import com.SchoolManagementSystem.system.entity.finance.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Long> {
    List<Fee> findByStudentId(Long studentId);

    List<Fee> findByStudentIdAndFeeStructureSemesterId(Long studentId, Long semesterId);

    boolean existsByStudentIdAndFeeStructureId(Long studentId, Long feeStructureId);
}
