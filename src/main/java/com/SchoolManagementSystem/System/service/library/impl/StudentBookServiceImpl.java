package com.SchoolManagementSystem.System.service.library.impl;

import com.SchoolManagementSystem.System.dto.library.StudentBookDto;
import com.SchoolManagementSystem.System.dtoMapper.library.StudentBookMapper;
import com.SchoolManagementSystem.System.entity.library.StudentBook;
import com.SchoolManagementSystem.System.repository.library.StudentBookRepository;
import com.SchoolManagementSystem.System.service.library.StudentBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentBookServiceImpl implements StudentBookService {

    private final StudentBookRepository repository;

    @Override
    public StudentBookDto save(StudentBookDto dto) {
        StudentBook book = StudentBookMapper.toEntity(dto);
        book = repository.save(book);
        return StudentBookMapper.toDto(book);
    }

    @Override
    public StudentBookDto update(Long id, StudentBookDto dto) {
        StudentBook book = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("StudentBook not found"));

        book.setGivenDate(dto.givenDate());

        book = repository.save(book);
        return StudentBookMapper.toDto(book);
    }

    @Override
    public StudentBookDto getById(Long id) {
        return repository.findById(id)
                .map(StudentBookMapper::toDto)
                .orElseThrow(() -> new RuntimeException("StudentBook not found"));
    }

    @Override
    public List<StudentBookDto> getAll() {
        return repository.findAll()
                .stream()
                .map(StudentBookMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
