package com.SchoolManagementSystem.System.service.library.impl;

import com.SchoolManagementSystem.System.dto.library.LibraryBookDto;
import com.SchoolManagementSystem.System.dtoMapper.library.LibraryBookMapper;
import com.SchoolManagementSystem.System.entity.library.LibraryBook;
import com.SchoolManagementSystem.System.repository.library.LibraryBookRepository;
import com.SchoolManagementSystem.System.service.library.LibraryBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LibraryBookServiceImpl implements LibraryBookService {

    private final LibraryBookRepository repository;

    @Override
    public LibraryBookDto save(LibraryBookDto dto) {
        LibraryBook book = LibraryBookMapper.toEntity(dto);
        book = repository.save(book);
        return LibraryBookMapper.toDto(book);
    }

    @Override
    public LibraryBookDto update(Long id, LibraryBookDto dto) {
        LibraryBook book = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("LibraryBook not found"));

        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setIsbn(dto.isbn());
        book.setCategory(dto.category());
        book.setDescription(dto.description());

        book = repository.save(book);
        return LibraryBookMapper.toDto(book);
    }

    @Override
    public LibraryBookDto getById(Long id) {
        return repository.findById(id)
                .map(LibraryBookMapper::toDto)
                .orElseThrow(() -> new RuntimeException("LibraryBook not found"));
    }

    @Override
    public List<LibraryBookDto> getAll() {
        return repository.findAll()
                .stream()
                .map(LibraryBookMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
