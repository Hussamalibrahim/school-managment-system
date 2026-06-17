package com.SchoolManagementSystem.System.service.library.impl;

import com.SchoolManagementSystem.System.dto.library.SchoolBookDto;
import com.SchoolManagementSystem.System.dtoMapper.library.SchoolBookMapper;
import com.SchoolManagementSystem.System.entity.library.SchoolBook;
import com.SchoolManagementSystem.System.repository.library.SchoolBookRepository;
import com.SchoolManagementSystem.System.service.library.SchoolBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SchoolBookServiceImpl implements SchoolBookService {

    private final SchoolBookRepository repository;

    @Override
    public SchoolBookDto save(SchoolBookDto dto) {
        SchoolBook book = SchoolBookMapper.toEntity(dto);
        book = repository.save(book);
        return SchoolBookMapper.toDto(book);
    }

    @Override
    public SchoolBookDto update(Long id, SchoolBookDto dto) {
        SchoolBook book = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("SchoolBook not found"));

        book.setName(dto.name());

        book = repository.save(book);
        return SchoolBookMapper.toDto(book);
    }

    @Override
    public SchoolBookDto getById(Long id) {
        return repository.findById(id)
                .map(SchoolBookMapper::toDto)
                .orElseThrow(() -> new RuntimeException("SchoolBook not found"));
    }

    @Override
    public List<SchoolBookDto> getAll() {
        return repository.findAll()
                .stream()
                .map(SchoolBookMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
