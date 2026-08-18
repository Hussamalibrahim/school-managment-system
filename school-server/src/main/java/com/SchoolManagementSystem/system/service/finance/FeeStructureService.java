package com.SchoolManagementSystem.system.service.finance;

import com.SchoolManagementSystem.system.dto.finance.request.FeeStructureRequest;
import com.SchoolManagementSystem.system.dto.finance.response.FeeStructureDto;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;

import java.util.List;

public interface FeeStructureService {
    FeeStructureDto save(FeeStructureRequest request);

    FeeStructureDto update(Long id, FeeStructureRequest request);

    FeeStructureDto getById(Long id);

    List<FeeStructureDto> getAll();

    List<FeeStructureDto> getBySemester(Long semesterId);

    List<FeeStructureDto> getBySemesterAndGradeForCurrentYear(SemesterName semesterName, GradeLevel gradeLevel);
    List<FeeStructureDto> getBySemesterAndGrade(Long semesterId, GradeLevel gradeLevel);

    void delete(Long id);

}
