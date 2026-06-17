package com.SchoolManagementSystem.System.service.academic.impl;

import com.SchoolManagementSystem.System.dto.academic.ClassDto;
import com.SchoolManagementSystem.System.entity.academic.Class;
import com.SchoolManagementSystem.System.dtoMapper.academic.ClassMapper;
import com.SchoolManagementSystem.System.repository.academic.ClassRepository;
import com.SchoolManagementSystem.System.service.academic.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClassServiceImpl implements ClassService {

    private final ClassRepository repository;

    @Override
    public ClassDto save(ClassDto dto) {
        Class clazz = ClassMapper.toEntity(dto);
        clazz = repository.save(clazz);
        return ClassMapper.toDto(clazz);
    }

    @Override
    public ClassDto update(Long id, ClassDto dto) {
        Class clazz = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        clazz.setLevel(dto.level());
        clazz.setSection(dto.section());
        clazz.setLocation(dto.location());
        clazz.setCapacity(dto.capacity());

        clazz = repository.save(clazz);
        return ClassMapper.toDto(clazz);
    }

    @Override
    public ClassDto getById(Long id) {
        return repository.findById(id)
                .map(ClassMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Class not found"));
    }

    @Override
    public List<ClassDto> getAll() {
        return repository.findAll()
                .stream()
                .map(ClassMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}