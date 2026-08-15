package com.SchoolManagementSystem.system.service.library.impl;

import com.SchoolManagementSystem.system.dto.library.LibraryDto;
import com.SchoolManagementSystem.system.mapper.library.LibraryMapper;
import com.SchoolManagementSystem.system.entity.library.Library;
import com.SchoolManagementSystem.system.repository.library.LibraryRepository;
import com.SchoolManagementSystem.system.service.library.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository repository;

    @Override
    public LibraryDto save(LibraryDto dto) {
        Library library = LibraryMapper.toEntity(dto);
        library = repository.save(library);
        return LibraryMapper.toDto(library);
    }

    @Override
    public LibraryDto update(Long id, LibraryDto dto) {
        Library library = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Library not found"));

        library = repository.save(library);
        return LibraryMapper.toDto(library);
    }

    @Override
    public LibraryDto getById(Long id) {
        return repository.findById(id)
                .map(LibraryMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Library not found"));
    }

    @Override
    public List<LibraryDto> getAll() {
        return repository.findAll()
                .stream()
                .map(LibraryMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

