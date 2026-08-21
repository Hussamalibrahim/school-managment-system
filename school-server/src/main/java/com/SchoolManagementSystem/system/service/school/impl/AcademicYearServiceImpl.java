package com.SchoolManagementSystem.system.service.school.impl;

import com.SchoolManagementSystem.system.dto.academic.request.AcademicYearWithSemestersRequest;
import com.SchoolManagementSystem.system.entity.academic.Semester;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.repository.academic.SemesterRepository;
import com.SchoolManagementSystem.system.service.school.AcademicYearService;
import com.SchoolManagementSystem.system.utils.ValidDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.SchoolManagementSystem.system.dto.school.AcademicYearDto;
import com.SchoolManagementSystem.system.mapper.school.AcademicYearMapper;
import com.SchoolManagementSystem.system.entity.school.AcademicYear;
import com.SchoolManagementSystem.system.repository.school.AcademicYearRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.SchoolManagementSystem.system.utils.ValidDate.validateDates;

@Service
@RequiredArgsConstructor
@Transactional
public class AcademicYearServiceImpl implements AcademicYearService {

    private final AcademicYearRepository academicYearRepository;
    private final SemesterRepository semesterRepository;

    @Override
    public AcademicYearDto save(AcademicYearDto dto) {
        AcademicYear academicYear = AcademicYearMapper.toEntity(dto);
        academicYear = academicYearRepository.save(academicYear);
        return AcademicYearMapper.toDto(academicYear);
    }

    @Override
    public AcademicYearDto update(Long id, AcademicYearDto dto) {
        AcademicYear academicYear = academicYearRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ACADEMIC_YEAR_NOT_FOUNT));

        academicYear.setName(dto.name());
        academicYear.setStartDate(dto.startDate());
        academicYear.setEndDate(dto.endDate());
        academicYear.setCurrentYear(dto.currentYear());

        academicYear = academicYearRepository.save(academicYear);
        return AcademicYearMapper.toDto(academicYear);
    }

    @Override
    public AcademicYearDto getById(Long id) {
        return academicYearRepository.findById(id)
                .map(AcademicYearMapper::toDto)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ACADEMIC_YEAR_NOT_FOUNT));
    }

    @Override
    public List<AcademicYearDto> getAll() {
        return academicYearRepository.findAll()
                .stream()
                .map(AcademicYearMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        academicYearRepository.deleteById(id);
    }

    //TODO  FIX IT
    @Transactional
    public void createAcademicYear(AcademicYearDto academicYear) {

        validateDates(academicYear.startDate(), academicYear.endDate());
        AcademicYear saved = academicYearRepository.save(AcademicYearMapper.toEntity(academicYear));

        Semester first = new Semester();

        first.setAcademicYear(saved);
        first.setSemesterName(SemesterName.FIRST);
        first.setStartDate(LocalDate.from(LocalDateTime.of(academicYear.startDate().getYear(), 8, 21, 0, 0)));
        first.setEndDate(LocalDate.from(LocalDateTime.of(academicYear.startDate().getYear() + 1, 1, 15, 0, 0)));

        Semester second = new Semester();

        second.setAcademicYear(saved);
        second.setSemesterName(SemesterName.SECOND);
        second.setStartDate(LocalDate.from(LocalDateTime.of(academicYear.startDate().getYear() + 1, 2, 1, 0, 0)));
        second.setEndDate(LocalDate.from(LocalDateTime.of(academicYear.startDate().getYear() + 1, 6, 21, 0, 0)));

        semesterRepository.saveAll(List.of(first, second));
    }

    @Transactional
    public void createAcademicYearWithSemesters(AcademicYearWithSemestersRequest request) {

        LocalDate firstStart = request.semesterUpdateRequest1().startDate();
        LocalDate firstEnd = request.semesterUpdateRequest1().endDate();

        LocalDate secondStart = request.semesterUpdateRequest2().startDate();
        LocalDate secondEnd = request.semesterUpdateRequest2().endDate();

        ValidDate.validateDates(firstStart, firstEnd,
                secondStart, secondEnd);

        AcademicYear saved = academicYearRepository.save(AcademicYearMapper.toEntity(request.academicYearDto()));

        Semester first = new Semester();

        first.setAcademicYear(saved);
        first.setSemesterName(SemesterName.FIRST);
        first.setStartDate(firstStart);
        first.setEndDate(firstEnd);

        Semester second = new Semester();

        second.setAcademicYear(saved);
        second.setSemesterName(SemesterName.SECOND);
        second.setStartDate(secondStart);
        second.setEndDate(secondEnd);

        semesterRepository.saveAll(
                List.of(first, second)
        );
    }

    public AcademicYearDto getCurrentAcademicYear() {
        return academicYearRepository.findByCurrentYearTrue()
                .map(AcademicYearMapper::toDto)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ACADEMIC_YEAR_NOT_FOUNT));
    }

}