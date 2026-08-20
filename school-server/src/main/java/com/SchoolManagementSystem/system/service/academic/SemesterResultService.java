package com.SchoolManagementSystem.system.service.academic;

import com.SchoolManagementSystem.system.dto.academic.SemesterResultDto;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;

import java.util.List;

public interface SemesterResultService {

    void finalizeSemester(SemesterName semesterName);

    List<SemesterResultDto> getByStudent(Long studentId, Long semesterId);

    List<SemesterResultDto> getBySemester(Long semesterId);

    SemesterResultDto getById(Long id);
}