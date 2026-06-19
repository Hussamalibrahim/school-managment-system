package com.SchoolManagementSystem.System.service.finance.impl;

import com.SchoolManagementSystem.System.dto.finance.FeeTypeDto;
import com.SchoolManagementSystem.System.mapper.finance.FeeTypeMapper;
import com.SchoolManagementSystem.System.entity.finance.FeeType;
import com.SchoolManagementSystem.System.repository.finance.FeeTypeRepository;
import com.SchoolManagementSystem.System.service.finance.FeeTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeeTypeServiceImpl implements FeeTypeService {

    private final FeeTypeRepository repository;

    @Override
    public FeeTypeDto save(FeeTypeDto dto) {
        FeeType feeType = FeeTypeMapper.toEntity(dto);
        feeType = repository.save(feeType);
        return FeeTypeMapper.toDto(feeType);
    }

    @Override
    public FeeTypeDto update(Long id, FeeTypeDto dto) {
        FeeType feeType = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("FeeType not found"));

        feeType.setName(dto.name());

        feeType = repository.save(feeType);
        return FeeTypeMapper.toDto(feeType);
    }

    @Override
    public FeeTypeDto getById(Long id) {
        return repository.findById(id)
                .map(FeeTypeMapper::toDto)
                .orElseThrow(() -> new RuntimeException("FeeType not found"));
    }

    @Override
    public List<FeeTypeDto> getAll() {
        return repository.findAll()
                .stream()
                .map(FeeTypeMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}