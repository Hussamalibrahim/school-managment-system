package com.SchoolManagementSystem.System.service.school.impl;

import com.SchoolManagementSystem.System.service.school.AcademicYearService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.SchoolManagementSystem.System.dto.school.AcademicYearDto;
import com.SchoolManagementSystem.System.dtoMapper.school.AcademicYearMapper;
import com.SchoolManagementSystem.System.entity.school.AcademicYear;
import com.SchoolManagementSystem.System.repository.school.AcademicYearRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AcademicYearServiceImpl implements AcademicYearService {

    private final AcademicYearRepository repository;

    @Override
    public AcademicYearDto save(AcademicYearDto dto) {
        AcademicYear academicYear = AcademicYearMapper.toEntity(dto);
        academicYear = repository.save(academicYear);
        return AcademicYearMapper.toDto(academicYear);
    }

    @Override
    public AcademicYearDto update(Long id, AcademicYearDto dto) {
        AcademicYear academicYear = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("AcademicYear not found"));

        academicYear.setName(dto.name());
        academicYear.setStartDate(dto.startDate());
        academicYear.setEndDate(dto.endDate());
        academicYear.setCurrentYear(dto.currentYear());

        academicYear = repository.save(academicYear);
        return AcademicYearMapper.toDto(academicYear);
    }

    @Override
    public AcademicYearDto getById(Long id) {
        return repository.findById(id)
                .map(AcademicYearMapper::toDto)
                .orElseThrow(() -> new RuntimeException("AcademicYear not found"));
    }

    @Override
    public List<AcademicYearDto> getAll() {
        return repository.findAll()
                .stream()
                .map(AcademicYearMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}