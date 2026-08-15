package com.SchoolManagementSystem.system.service.academic.impl;

import com.SchoolManagementSystem.system.dto.academic.EducationRecordDto;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.academic.EducationRecordMapper;
import com.SchoolManagementSystem.system.entity.academic.EducationRecord;
import com.SchoolManagementSystem.system.repository.academic.EducationRecordRepository;
import com.SchoolManagementSystem.system.service.academic.EducationRecordService;
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
    @Transactional
    public EducationRecordDto save(EducationRecordDto dto) {
        return EducationRecordMapper.toDto(
                repository.save(
                        EducationRecordMapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public EducationRecordDto update(Long id, EducationRecordDto dto) {
        EducationRecord record = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EDUCATION_RECORD_NOT_FOUND));

        EducationRecordMapper.updateEntity(record, dto);

        return EducationRecordMapper.toDto(repository.save(record));
    }

    @Override
    @Transactional(readOnly = true)
    public EducationRecordDto getById(Long id) {
        return repository.findById(id)
                .map(EducationRecordMapper::toDto)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EDUCATION_RECORD_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EducationRecordDto> getAll() {
        return repository.findAll()
                .stream()
                .map(EducationRecordMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.delete(
                repository.findById(id)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.EDUCATION_RECORD_NOT_FOUND)));
    }
}
