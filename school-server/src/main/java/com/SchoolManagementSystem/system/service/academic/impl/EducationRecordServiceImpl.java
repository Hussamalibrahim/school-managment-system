package com.SchoolManagementSystem.system.service.academic.impl;

import com.SchoolManagementSystem.system.dto.academic.EducationRecordDto;
import com.SchoolManagementSystem.system.entity.academic.EducationRecord;
import com.SchoolManagementSystem.system.entity.enumeration.AttendanceStatus;
import com.SchoolManagementSystem.system.entity.school.AcademicYear;
import com.SchoolManagementSystem.system.entity.student.Student;
import com.SchoolManagementSystem.system.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.academic.EducationRecordMapper;
import com.SchoolManagementSystem.system.repository.academic.EducationRecordRepository;
import com.SchoolManagementSystem.system.repository.school.AcademicYearRepository;
import com.SchoolManagementSystem.system.repository.student.AttendanceRepository;
import com.SchoolManagementSystem.system.repository.student.StudentRepository;
import com.SchoolManagementSystem.system.service.academic.EducationRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationRecordServiceImpl implements EducationRecordService {

    private final EducationRecordRepository educationRecordRepository;
    private final StudentRepository studentRepository;
    private final AcademicYearRepository academicYearRepository;
    private final AttendanceRepository attendanceRepository;

    @Override
    public EducationRecordDto create(
            Long studentId,
            Long academicYearId
    ) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new NotFoundException(
                                ErrorCode.STUDENT_NOT_FOUND
                        ));

        AcademicYear academicYear =
                academicYearRepository.findById(academicYearId)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        ErrorCode.ACADEMIC_YEAR_NOT_FOUNT
                                ));

        if (educationRecordRepository
                .findByStudentIdAndAcademicYearId(
                        studentId,
                        academicYearId
                ).isPresent()) {

            throw new AlreadyExistsException(
                    ErrorCode.EDUCATION_RECORD_ALREADY_EXISTS
            );
        }

        long absenceDays =
                attendanceRepository
                        .countByStudentIdAndAttendanceStatusAndAttendanceDateBetween(
                                studentId,
                                AttendanceStatus.ABSENT,
                                academicYear.getStartDate(),
                                academicYear.getEndDate()
                        );

        EducationRecord record = new EducationRecord();

        record.setStudent(student);
        record.setAcademicYear(academicYear);

        // الصف الذي كان فيه الطالب خلال هذه السنة
        record.setGradeLevel(student.getGradeLevel());

        record.setAbsenceDays((int) absenceDays);

        EducationRecord saved =
                educationRecordRepository.save(record);

        return EducationRecordMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EducationRecordDto getById(Long id) {

        return educationRecordRepository.findById(id)
                .map(EducationRecordMapper::toDto)
                .orElseThrow(() ->
                        new NotFoundException(
                                ErrorCode.EDUCATION_RECORD_NOT_FOUND
                        ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EducationRecordDto> getStudentRecords(
            Long studentId
    ) {

        if (!studentRepository.existsById(studentId)) {
            throw new NotFoundException(
                    ErrorCode.STUDENT_NOT_FOUND
            );
        }

        return educationRecordRepository
                .findByStudentIdOrderByAcademicYearStartDateDesc(studentId)
                .stream()
                .map(EducationRecordMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EducationRecordDto> getByAcademicYear(
            Long academicYearId
    ) {

        if (!academicYearRepository.existsById(academicYearId)) {
            throw new NotFoundException(
                    ErrorCode.ACADEMIC_YEAR_NOT_FOUNT
            );
        }

        return educationRecordRepository
                .findByAcademicYearId(academicYearId)
                .stream()
                .map(EducationRecordMapper::toDto)
                .toList();
    }

    @Override
    public EducationRecordDto update(
            Long id,
            EducationRecordDto dto
    ) {

        EducationRecord record =
                educationRecordRepository.findById(id)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        ErrorCode.EDUCATION_RECORD_NOT_FOUND
                                ));

        EducationRecordMapper.updateEntity(record, dto);

        return EducationRecordMapper.toDto(
                educationRecordRepository.save(record)
        );
    }
}