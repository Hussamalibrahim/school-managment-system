package com.SchoolManagementSystem.System.service.user.impl;

import com.SchoolManagementSystem.System.dto.user.PrincipalDto;
import com.SchoolManagementSystem.System.dtoMapper.user.PrincipalMapper;
import com.SchoolManagementSystem.System.entity.user.Principal;
import com.SchoolManagementSystem.System.repository.user.PrincipalRepository;
import com.SchoolManagementSystem.System.service.user.PrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PrincipalServiceImpl implements PrincipalService {

    private final PrincipalRepository repository;

    @Override
    public PrincipalDto save(PrincipalDto dto) {
        Principal principal = PrincipalMapper.toEntity(dto);
        principal = repository.save(principal);
        return PrincipalMapper.toDto(principal);
    }

    @Override
    public PrincipalDto update(Long id, PrincipalDto dto) {
        Principal principal = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Principal not found"));

        principal.setNationalId(dto.nationalId());
        principal.setFirstName(dto.firstName());
        principal.setLastName(dto.lastName());
        principal.setPhone(dto.phone());
        principal.setEmail(dto.email());
        principal.setAddress(dto.address());
        principal.setStatus(dto.status());
        principal.setHireDate(dto.hireDate());

        principal = repository.save(principal);
        return PrincipalMapper.toDto(principal);
    }

    @Override
    public PrincipalDto getById(Long id) {
        return repository.findById(id)
                .map(PrincipalMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Principal not found"));
    }

    @Override
    public List<PrincipalDto> getAll() {
        return repository.findAll()
                .stream()
                .map(PrincipalMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
