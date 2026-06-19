package com.SchoolManagementSystem.System.service.academic.impl;

import com.SchoolManagementSystem.System.dto.academic.EducationRecordDto;
import com.SchoolManagementSystem.System.mapper.academic.EducationRecordMapper;
import com.SchoolManagementSystem.System.entity.academic.EducationRecord;
import com.SchoolManagementSystem.System.repository.academic.EducationRecordRepository;
import com.SchoolManagementSystem.System.service.academic.EducationRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationRecordServiceImpl implements EducationRecordService {

    private final EducationRecordRepository repository;

    @Override
    public EducationRecordDto save(EducationRecordDto dto) {
        EducationRecord record = EducationRecordMapper.toEntity(dto);
        record = repository.save(record);
        return EducationRecordMapper.toDto(record);
    }

    @Override
    public EducationRecordDto update(Long id, EducationRecordDto dto) {
        EducationRecord record = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("EducationRecord not found"));

        record.setFinalAverage(dto.finalAverage());
        record.setPassed(dto.passed());
        record.setAbsenceDays(dto.absenceDays());
        record.setNotes(dto.notes());

        record = repository.save(record);
        return EducationRecordMapper.toDto(record);
    }

    @Override
    public EducationRecordDto getById(Long id) {
        return repository.findById(id)
                .map(EducationRecordMapper::toDto)
                .orElseThrow(() -> new RuntimeException("EducationRecord not found"));
    }

    @Override
    public List<EducationRecordDto> getAll() {
        return repository.findAll()
                .stream()
                .map(EducationRecordMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
