package com.SchoolManagementSystem.system.service.user.impl;

import com.SchoolManagementSystem.system.dto.user.LibrarianDto;
import com.SchoolManagementSystem.system.mapper.user.LibrarianMapper;
import com.SchoolManagementSystem.system.entity.user.Librarian;
import com.SchoolManagementSystem.system.repository.user.LibrarianRepository;
import com.SchoolManagementSystem.system.service.user.LibrarianService;
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
        librarian.setAddress(dto.address());
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
