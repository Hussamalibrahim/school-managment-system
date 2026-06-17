package com.SchoolManagementSystem.System.service.finance.impl;

import com.SchoolManagementSystem.System.dto.finance.StudentDiscountDto;
import com.SchoolManagementSystem.System.dtoMapper.finance.StudentDiscountMapper;
import com.SchoolManagementSystem.System.entity.finance.StudentDiscount;
import com.SchoolManagementSystem.System.repository.finance.StudentDiscountRepository;
import com.SchoolManagementSystem.System.service.finance.StudentDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentDiscountServiceImpl implements StudentDiscountService {

    private final StudentDiscountRepository repository;

    @Override
    public StudentDiscountDto save(StudentDiscountDto dto) {
        StudentDiscount studentDiscount = StudentDiscountMapper.toEntity(dto);
        studentDiscount = repository.save(studentDiscount);
        return StudentDiscountMapper.toDto(studentDiscount);
    }

    @Override
    public StudentDiscountDto update(Long id, StudentDiscountDto dto) {
        StudentDiscount studentDiscount = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("StudentDiscount not found"));

        studentDiscount = repository.save(studentDiscount);

        return StudentDiscountMapper.toDto(studentDiscount);
    }

    @Override
    public StudentDiscountDto getById(Long id) {
        return repository.findById(id)
                .map(StudentDiscountMapper::toDto)
                .orElseThrow(() -> new RuntimeException("StudentDiscount not found"));
    }

    @Override
    public List<StudentDiscountDto> getAll() {
        return repository.findAll()
                .stream()
                .map(StudentDiscountMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
