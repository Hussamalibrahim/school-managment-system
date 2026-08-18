package com.SchoolManagementSystem.system.service.finance.impl;

import com.SchoolManagementSystem.system.dto.finance.request.FeeStructureRequest;
import com.SchoolManagementSystem.system.dto.finance.response.FeeStructureDto;
import com.SchoolManagementSystem.system.entity.academic.Semester;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.system.entity.finance.FeeStructure;
import com.SchoolManagementSystem.system.entity.school.AcademicYear;
import com.SchoolManagementSystem.system.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.finance.FeeStructureMapper;
import com.SchoolManagementSystem.system.mapper.school.AcademicYearMapper;
import com.SchoolManagementSystem.system.repository.academic.SemesterRepository;
import com.SchoolManagementSystem.system.repository.finance.FeeStructureRepository;
import com.SchoolManagementSystem.system.repository.school.AcademicYearRepository;
import com.SchoolManagementSystem.system.service.finance.FeeService;
import com.SchoolManagementSystem.system.service.finance.FeeStructureService;
import com.SchoolManagementSystem.system.service.school.AcademicYearService;
import com.SchoolManagementSystem.system.service.school.impl.AcademicYearServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeeStructureServiceImpl implements FeeStructureService {

    private final FeeStructureRepository feeStructureRepository;
    private final SemesterRepository semesterRepository;
    private final AcademicYearService academicYearService;
    private final AcademicYearRepository academicYearRepository;
    private final FeeService feeService;

    @Override
    public FeeStructureDto save(FeeStructureRequest request) {

        validateRequest(request);

        Semester semester = semesterRepository.findById(request.semesterId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));

        if (feeStructureRepository.existsBySemesterIdAndGradeLevelAndFeeType(
                request.semesterId()
                , request.gradeLevel()
                , request.feeType())) {
            throw new AlreadyExistsException(ErrorCode.FEE_STRUCTURE_ALREADY_EXISTS);
        }

        FeeStructure entity = FeeStructureMapper.toEntity(request);
        entity.setSemester(semester);

        FeeStructure saved = feeStructureRepository.saveAndFlush(entity);

        feeService.applyFeeStructureToStudents(entity);
        return FeeStructureMapper.toDto(saved);
    }

    @Override
    public FeeStructureDto update(Long id, FeeStructureRequest request) {

        validateRequest(request);

        FeeStructure entity = findById(id);

        Semester semester = semesterRepository.findById(request.semesterId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));

        boolean duplicate = feeStructureRepository.findBySemesterIdAndGradeLevelAndFeeType(
                        request.semesterId(),
                        request.gradeLevel(),
                        request.feeType())
                .filter(existing -> !existing.getId().equals(id))
                .isPresent();

        if (duplicate) {
            throw new AlreadyExistsException(ErrorCode.FEE_STRUCTURE_ALREADY_EXISTS);
        }

        FeeStructureMapper.updateEntity(entity, request);

        entity.setSemester(semester);
        return FeeStructureMapper.toDto(feeStructureRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public FeeStructureDto getById(Long id) {

        return FeeStructureMapper.toDto(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeeStructureDto> getAll() {

        return feeStructureRepository
                .findAll()
                .stream()
                .map(FeeStructureMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeeStructureDto> getBySemester(Long semesterId) {

        semesterRepository.findById(semesterId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));

        return feeStructureRepository
                .findBySemesterId(semesterId)
                .stream()
                .map(FeeStructureMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeeStructureDto> getBySemesterAndGradeForCurrentYear(SemesterName semesterName, GradeLevel gradeLevel) {

        AcademicYear academicYear = AcademicYearMapper.toEntity(academicYearService.getCurrentAcademicYear());

        Semester semester = semesterRepository.findByAcademicYearIdAndSemesterName(academicYear.getId(), semesterName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));

        return feeStructureRepository
                .findBySemesterIdAndGradeLevel(
                        semester.getId(),
                        gradeLevel
                ).stream()
                .map(FeeStructureMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeeStructureDto> getBySemesterAndGrade(Long semesterId, GradeLevel gradeLevel) {

        AcademicYear academicYear = AcademicYearMapper.toEntity(academicYearService.getCurrentAcademicYear());

        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));

        return feeStructureRepository
                .findBySemesterIdAndGradeLevel(
                        semester.getId(),
                        gradeLevel
                ).stream()
                .map(FeeStructureMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {

        FeeStructure entity = findById(id);
        feeStructureRepository.delete(entity);
    }

    private FeeStructure findById(Long id) {

        return feeStructureRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FEE_STRUCTURE_NOT_FOUND));
    }

    private void validateRequest(
            FeeStructureRequest request) {

        if (request.semesterId() == null || request.gradeLevel() == null ||
                request.feeType() == null || request.amount() == null || request.dueDate() == null) {

            throw new ValidationException(ErrorCode.INVALID_FEE_STRUCTURE);
        }

        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new ValidationException(ErrorCode.INVALID_FEE_AMOUNT);
        }
    }
}