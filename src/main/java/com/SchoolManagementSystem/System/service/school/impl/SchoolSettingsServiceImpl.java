package com.SchoolManagementSystem.System.service.school.impl;

import com.SchoolManagementSystem.System.dto.school.SchoolSettingsDto;
import com.SchoolManagementSystem.System.dtoMapper.school.SchoolSettingsMapper;
import com.SchoolManagementSystem.System.entity.school.SchoolSettings;
import com.SchoolManagementSystem.System.repository.school.SchoolSettingsRepository;
import com.SchoolManagementSystem.System.service.school.SchoolSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SchoolSettingsServiceImpl implements SchoolSettingsService {

    private final SchoolSettingsRepository repository;

    @Override
    public SchoolSettingsDto save(SchoolSettingsDto dto) {
        SchoolSettings settings = SchoolSettingsMapper.toEntity(dto);
        settings = repository.save(settings);
        return SchoolSettingsMapper.toDto(settings);
    }

    @Override
    public SchoolSettingsDto update(Long id, SchoolSettingsDto dto) {
        SchoolSettings settings = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("SchoolSettings not found"));

        settings.setSchoolStartTime(dto.schoolStartTime());
        settings.setPeriodDurationMinutes(dto.periodDurationMinutes());
        settings.setBreakDurationMinutes(dto.breakDurationMinutes());
        settings.setMaxAllowedAbsences(dto.maxAllowedAbsences());

        settings = repository.save(settings);
        return SchoolSettingsMapper.toDto(settings);
    }

    @Override
    public SchoolSettingsDto getById(Long id) {
        return repository.findById(id)
                .map(SchoolSettingsMapper::toDto)
                .orElseThrow(() -> new RuntimeException("SchoolSettings not found"));
    }

    @Override
    public List<SchoolSettingsDto> getAll() {
        return repository.findAll()
                .stream()
                .map(SchoolSettingsMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
