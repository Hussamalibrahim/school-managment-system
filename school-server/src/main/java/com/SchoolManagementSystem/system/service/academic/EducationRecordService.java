package com.SchoolManagementSystem.system.service.academic;

import com.SchoolManagementSystem.system.dto.academic.EducationRecordDto;

import java.util.List;

public interface EducationRecordService {

    EducationRecordDto create(
            Long studentId,
            Long academicYearId
    );

    EducationRecordDto getById(Long id);

    List<EducationRecordDto> getStudentRecords(Long studentId);

    List<EducationRecordDto> getByAcademicYear(Long academicYearId);

    EducationRecordDto update(
            Long id,
            EducationRecordDto dto
    );
}