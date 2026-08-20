package com.SchoolManagementSystem.system.service.academic.impl;

import com.SchoolManagementSystem.system.dto.academic.SemesterDto;
import com.SchoolManagementSystem.system.dto.academic.request.SemesterUpdateRequest;
import com.SchoolManagementSystem.system.dto.academic.request.UpdateTwoSemesterRequest;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.system.entity.school.AcademicYear;
import com.SchoolManagementSystem.system.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.academic.SemesterMapper;
import com.SchoolManagementSystem.system.entity.academic.Semester;
import com.SchoolManagementSystem.system.repository.academic.SemesterRepository;
import com.SchoolManagementSystem.system.repository.school.AcademicYearRepository;
import com.SchoolManagementSystem.system.service.academic.SemesterService;
import com.SchoolManagementSystem.system.utils.ValidDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;
    private final AcademicYearRepository academicYearRepository;

    @Override
    @Transactional
    public SemesterDto save(SemesterDto dto) {
        if (semesterRepository.existsBySemesterName(dto.semesterName()))
            throw new AlreadyExistsException(ErrorCode.SEMESTER_ALREADY_EXISTS);
        return SemesterMapper.toDto(
                semesterRepository.save(SemesterMapper
                        .toEntity(dto)));
    }

    @Transactional
    @Override
    public SemesterDto update(Long id, SemesterDto dt) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public SemesterDto getById(Long id) {
        return semesterRepository.findById(id)
                .map(SemesterMapper::toDto)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public SemesterDto getByName(SemesterName semesterName) {

        AcademicYear academicYear = academicYearRepository.findByCurrentYearTrue()
                        .orElseThrow(() -> new NotFoundException(ErrorCode.ACADEMIC_YEAR_NOT_FOUNT));

        Semester semester = semesterRepository
                .findByAcademicYearIdAndSemesterName(academicYear.getId(), semesterName)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));

        return SemesterMapper.toDto(semester);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SemesterDto> getAll() {
        return semesterRepository.findAll()
                .stream()
                .map(SemesterMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        semesterRepository.deleteById(
                semesterRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND)));
    }

    @Override
    public void updateSemester(Long academicYearId, SemesterUpdateRequest semesterUpdateRequest) {

        AcademicYear academicYear = academicYearRepository.findById(academicYearId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ACADEMIC_YEAR_NOT_FOUNT));

        Semester semester = semesterRepository.findByAcademicYearIdAndSemesterName(academicYearId, semesterUpdateRequest.semesterName()).orElseThrow(() ->
                new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));

        if (semesterUpdateRequest.semesterName() == SemesterName.FIRST) {
            Semester semester2 = semesterRepository.findByAcademicYearIdAndSemesterName(academicYearId, SemesterName.SECOND)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));

            ValidDate.validateDates(
                    semesterUpdateRequest.startDate(),
                    semesterUpdateRequest.endDate(),
                    semester2.getStartDate(),
                    semester2.getEndDate());
        } else {
            Semester semester2 = semesterRepository.findByAcademicYearIdAndSemesterName(academicYearId, SemesterName.FIRST)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));

            ValidDate.validateDates(
                    semester2.getStartDate(),
                    semester2.getEndDate(),
                    semesterUpdateRequest.startDate(),
                    semesterUpdateRequest.endDate());
        }

        SemesterMapper.updateEntity(semester, semesterUpdateRequest);
        semesterRepository.save(semester);
    }

    @Override
    @Transactional
    public void updateTwoSemester(Long academicYearId, UpdateTwoSemesterRequest request) {

        AcademicYear academicYear = academicYearRepository.findById(academicYearId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ACADEMIC_YEAR_NOT_FOUNT));


        SemesterUpdateRequest firstRequest = request.first();
        SemesterUpdateRequest secondRequest = request.second();

        if (firstRequest.semesterName() != SemesterName.FIRST ||
                secondRequest.semesterName() != SemesterName.SECOND) {

            throw new ValidationException(ErrorCode.INVALID_SEMESTER);
        }

        ValidDate.validateDates(
                firstRequest.startDate(),
                firstRequest.endDate(),
                secondRequest.startDate(),
                secondRequest.endDate());

        Semester first = semesterRepository
                .findByAcademicYearIdAndSemesterName(academicYear.getId(), SemesterName.FIRST)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));

        Semester second = semesterRepository.findByAcademicYearIdAndSemesterName(
                        academicYear.getId(), SemesterName.SECOND)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));

        SemesterMapper.updateEntity(first, firstRequest);
        SemesterMapper.updateEntity(second, secondRequest);

        semesterRepository.saveAll(List.of(first, second));
    }

    public SemesterDto getCurrentSemester() {
        AcademicYear academicYear = academicYearRepository.findByCurrentYearTrue()
                .orElseThrow(() -> new NotFoundException(ErrorCode.ACADEMIC_YEAR_NOT_FOUNT));

        Semester semester = semesterRepository.findCurrentSemester(academicYear.getId(), LocalDate.now())
                .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));

        return SemesterMapper.toDto(semester);
    }


}
