package com.SchoolManagementSystem.System.service.library.impl;

import com.SchoolManagementSystem.System.dto.library.LibraryDto;
import com.SchoolManagementSystem.System.dtoMapper.library.LibraryMapper;
import com.SchoolManagementSystem.System.entity.library.Library;
import com.SchoolManagementSystem.System.repository.library.LibraryRepository;
import com.SchoolManagementSystem.System.service.library.LibraryService;
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

        library.setName(dto.name());

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

