package com.SchoolManagementSystem.System.service.student.impl;

import com.SchoolManagementSystem.System.dto.student.WarningDto;
import com.SchoolManagementSystem.System.mapper.student.WarningMapper;
import com.SchoolManagementSystem.System.entity.student.Warning;
import com.SchoolManagementSystem.System.repository.student.WarningRepository;
import com.SchoolManagementSystem.System.service.student.WarningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WarningServiceImpl implements WarningService {

    private final WarningRepository repository;

    @Override
    public WarningDto save(WarningDto dto) {
        Warning warning = WarningMapper.toEntity(dto);
        warning = repository.save(warning);
        return WarningMapper.toDto(warning);
    }

    @Override
    public WarningDto update(Long id, WarningDto dto) {
        Warning warning = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warning not found"));

        warning.setWarningDate(dto.warningDate());
        warning.setReason(dto.reason());

        warning = repository.save(warning);
        return WarningMapper.toDto(warning);
    }

    @Override
    public WarningDto getById(Long id) {
        return repository.findById(id)
                .map(WarningMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Warning not found"));
    }

    @Override
    public List<WarningDto> getAll() {
        return repository.findAll()
                .stream()
                .map(WarningMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
