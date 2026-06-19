package com.SchoolManagementSystem.System.service.user.impl;

import com.SchoolManagementSystem.System.dto.user.SecretaryDto;
import com.SchoolManagementSystem.System.mapper.user.SecretaryMapper;
import com.SchoolManagementSystem.System.entity.user.Secretary;
import com.SchoolManagementSystem.System.repository.user.SecretaryRepository;
import com.SchoolManagementSystem.System.service.user.SecretaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SecretaryServiceImpl implements SecretaryService {

    private final SecretaryRepository repository;

    @Override
    public SecretaryDto save(SecretaryDto dto) {
        Secretary secretary = SecretaryMapper.toEntity(dto);
        secretary = repository.save(secretary);
        return SecretaryMapper.toDto(secretary);
    }

    @Override
    public SecretaryDto update(Long id, SecretaryDto dto) {
        Secretary secretary = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Secretary not found"));

        secretary.setNationalId(dto.nationalId());
        secretary.setFirstName(dto.firstName());
        secretary.setLastName(dto.lastName());
        secretary.setPhone(dto.phone());
        secretary.setEmail(dto.email());
        secretary.setAddress(dto.address());
        secretary.setStatus(dto.status());
        secretary.setHireDate(dto.hireDate());

        secretary = repository.save(secretary);
        return SecretaryMapper.toDto(secretary);
    }

    @Override
    public SecretaryDto getById(Long id) {
        return repository.findById(id)
                .map(SecretaryMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Secretary not found"));
    }

    @Override
    public List<SecretaryDto> getAll() {
        return repository.findAll()
                .stream()
                .map(SecretaryMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}