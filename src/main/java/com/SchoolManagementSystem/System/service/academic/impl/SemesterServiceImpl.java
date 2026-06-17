package com.SchoolManagementSystem.System.service.academic.impl;

import com.SchoolManagementSystem.System.dto.academic.SemesterDto;
import com.SchoolManagementSystem.System.dtoMapper.academic.SemesterMapper;
import com.SchoolManagementSystem.System.entity.academic.Semester;
import com.SchoolManagementSystem.System.repository.academic.SemesterRepository;
import com.SchoolManagementSystem.System.service.academic.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository repository;

    @Override
    public SemesterDto save(SemesterDto dto) {
        Semester semester = SemesterMapper.toEntity(dto);
        semester = repository.save(semester);
        return SemesterMapper.toDto(semester);
    }

    @Override
    public SemesterDto update(Long id, SemesterDto dto) {
        Semester semester = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Semester not found"));

        semester.setName(dto.name());
        semester.setStartDate(dto.startDate());
        semester.setEndDate(dto.endDate());

        semester = repository.save(semester);
        return SemesterMapper.toDto(semester);
    }

    @Override
    public SemesterDto getById(Long id) {
        return repository.findById(id)
                .map(SemesterMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Semester not found"));
    }

    @Override
    public List<SemesterDto> getAll() {
        return repository.findAll()
                .stream()
                .map(SemesterMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
