package com.SchoolManagementSystem.System.service.finance.impl;

import com.SchoolManagementSystem.System.dto.finance.ClassFeeDto;
import com.SchoolManagementSystem.System.mapper.finance.ClassFeeMapper;
import com.SchoolManagementSystem.System.entity.finance.ClassFee;
import com.SchoolManagementSystem.System.repository.finance.ClassFeeRepository;
import com.SchoolManagementSystem.System.service.finance.ClassFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClassFeeServiceImpl implements ClassFeeService {

    private final ClassFeeRepository repository;

    @Override
    public ClassFeeDto save(ClassFeeDto dto) {
        ClassFee classFee = ClassFeeMapper.toEntity(dto);
        classFee = repository.save(classFee);
        return ClassFeeMapper.toDto(classFee);
    }

    @Override
    public ClassFeeDto update(Long id, ClassFeeDto dto) {
        ClassFee classFee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClassFee not found"));

        classFee.setAmount(dto.amount());

        classFee = repository.save(classFee);
        return ClassFeeMapper.toDto(classFee);
    }

    @Override
    public ClassFeeDto getById(Long id) {
        return repository.findById(id)
                .map(ClassFeeMapper::toDto)
                .orElseThrow(() -> new RuntimeException("ClassFee not found"));
    }

    @Override
    public List<ClassFeeDto> getAll() {
        return repository.findAll()
                .stream()
                .map(ClassFeeMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
