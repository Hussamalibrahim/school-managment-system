package com.SchoolManagementSystem.system.repository.finance;

import com.SchoolManagementSystem.system.entity.enumeration.FeeType;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.finance.FeeStructure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeeStructureRepository extends JpaRepository<FeeStructure, Long> {

    List<FeeStructure> findBySemesterId(Long semesterId);

    List<FeeStructure> findBySemesterIdAndGradeLevel(Long semesterId, GradeLevel gradeLevel);

    Optional<FeeStructure> findBySemesterIdAndGradeLevelAndFeeType(Long semesterId, GradeLevel gradeLevel, FeeType type);

    boolean existsBySemesterIdAndGradeLevelAndFeeType(Long semesterId, GradeLevel gradeLevel, FeeType type);

}