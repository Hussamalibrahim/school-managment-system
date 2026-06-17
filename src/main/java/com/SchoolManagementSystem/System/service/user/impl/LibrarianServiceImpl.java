package com.SchoolManagementSystem.System.service.user.impl;

import com.SchoolManagementSystem.System.dto.user.LibrarianDto;
import com.SchoolManagementSystem.System.dtoMapper.user.LibrarianMapper;
import com.SchoolManagementSystem.System.entity.user.Librarian;
import com.SchoolManagementSystem.System.repository.user.LibrarianRepository;
import com.SchoolManagementSystem.System.service.user.LibrarianService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LibrarianServiceImpl implements LibrarianService {

    private final LibrarianRepository repository;

    @Override
    public LibrarianDto save(LibrarianDto dto) {
        Librarian librarian = LibrarianMapper.toEntity(dto);
        librarian = repository.save(librarian);
        return LibrarianMapper.toDto(librarian);
    }

    @Override
    public LibrarianDto update(Long id, LibrarianDto dto) {
        Librarian librarian = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Librarian not found"));

        librarian.setNationalId(dto.nationalId());
        librarian.setFirstName(dto.firstName());
        librarian.setLastName(dto.lastName());
        librarian.setPhone(dto.phone());
        librarian.setEmail(dto.email());
        librarian.setAddress(dto.address());
        librarian.setStatus(dto.status());
        librarian.setHireDate(dto.hireDate());

        librarian = repository.save(librarian);
        return LibrarianMapper.toDto(librarian);
    }

    @Override
    public LibrarianDto getById(Long id) {
        return repository.findById(id)
                .map(LibrarianMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Librarian not found"));
    }

    @Override
    public List<LibrarianDto> getAll() {
        return repository.findAll()
                .stream()
                .map(LibrarianMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
