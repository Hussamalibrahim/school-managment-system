package com.SchoolManagementSystem.System.service.user.impl;

import com.SchoolManagementSystem.System.dto.user.GuardianDto;
import com.SchoolManagementSystem.System.mapper.user.GuardianMapper;
import com.SchoolManagementSystem.System.entity.user.Guardian;
import com.SchoolManagementSystem.System.repository.user.GuardianRepository;
import com.SchoolManagementSystem.System.service.user.GuardianService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GuardianServiceImpl implements GuardianService {

    private final GuardianRepository repository;

    @Override
    public GuardianDto save(GuardianDto dto) {
        Guardian guardian = GuardianMapper.toEntity(dto);
        guardian = repository.save(guardian);
        return GuardianMapper.toDto(guardian);
    }

    @Override
    public GuardianDto update(Long id, GuardianDto dto) {
        Guardian guardian = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guardian not found"));

        guardian.setNationalId(dto.nationalId());
        guardian.setFirstName(dto.firstName());
        guardian.setLastName(dto.lastName());
        guardian.setPhone(dto.phone());
        guardian.setEmail(dto.email());
        guardian.setAddress(dto.address());
        guardian.setOccupation(dto.occupation());

        guardian = repository.save(guardian);
        return GuardianMapper.toDto(guardian);
    }

    @Override
    public GuardianDto getById(Long id) {
        return repository.findById(id)
                .map(GuardianMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Guardian not found"));
    }

    @Override
    public List<GuardianDto> getAll() {
        return repository.findAll()
                .stream()
                .map(GuardianMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
