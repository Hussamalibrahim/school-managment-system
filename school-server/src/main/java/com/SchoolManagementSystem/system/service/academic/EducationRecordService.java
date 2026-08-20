package com.SchoolManagementSystem.system.service.academic;

import com.SchoolManagementSystem.system.dto.academic.EducationRecordDto;
import com.SchoolManagementSystem.system.dto.academic.StudentEducationHistoryDto;
import com.SchoolManagementSystem.system.dto.academic.response.*;

import java.util.List;

public interface EducationRecordService {

    EducationRecordDto getById(
            Long id
    );

    List<EducationRecordDto> getStudentRecords(
            Long studentId
    );

    List<EducationRecordDto> getPassedStudents(
            Long academicYearId
    );

    List<EducationRecordDto> getFailedStudents(
            Long academicYearId
    );

    AcademicYearStatisticsDto getAcademicYearStatistics(
            Long academicYearId
    );

    void promoteStudents(
            Long academicYearId
    );

    StudentAcademicStatisticsDto getStudentStatistics(
            Long studentId,
            Long academicYearId
    );

    List<SubjectStatisticsDto> getSubjectStatistics(
            Long academicYearId
    );

    void registerNextYear(
            Long educationRecordId,
            Long targetClassId
    );

    List<TopStudentDto> getTopStudents(
            Long academicYearId,
            int limit
    );

    List<StudentEducationHistoryDto> getStudentHistory(
            Long studentId
    );

    void generateEducationRecords(
            Long academicYearId
    );

    StudentYearStatisticsDto getStudentYearStatistics(
            Long studentId,
            Long academicYearId
    );

    Double getAcademicYearAverage(
            Long academicYearId
    );

    List<StudentEducationHistoryDto> getGuardianChildrenHistory(Long refId);
}