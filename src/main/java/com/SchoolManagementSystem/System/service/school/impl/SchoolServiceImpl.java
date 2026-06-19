package com.SchoolManagementSystem.System.service.school.impl;

import com.SchoolManagementSystem.System.dto.school.SchoolDto;
import com.SchoolManagementSystem.System.mapper.school.SchoolMapper;
import com.SchoolManagementSystem.System.entity.school.School;
import com.SchoolManagementSystem.System.repository.school.SchoolRepository;
import com.SchoolManagementSystem.System.service.school.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository repository;

    @Override
    public SchoolDto save(SchoolDto dto) {
        School school = SchoolMapper.toEntity(dto);
        school = repository.save(school);
        return SchoolMapper.toDto(school);
    }

    @Override
    public SchoolDto update(Long id, SchoolDto dto) {
        School school = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));

        school.setName(dto.name());
        school.setSchoolCategory(dto.schoolCategory());
        school.setAddress(dto.address());
        school.setPhone(dto.phone());
        school.setLogoPath(dto.logoPath());
        school.setSupportedStages(dto.supportedStages());

        school = repository.save(school);
        return SchoolMapper.toDto(school);
    }

    @Override
    public SchoolDto getById(Long id) {
        return repository.findById(id)
                .map(SchoolMapper::toDto)
                .orElseThrow(() -> new RuntimeException("School not found"));
    }

    @Override
    public List<SchoolDto> getAll() {
        return repository.findAll()
                .stream()
                .map(SchoolMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
