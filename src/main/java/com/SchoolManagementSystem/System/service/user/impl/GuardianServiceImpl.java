package com.SchoolManagementSystem.System.service.user.impl;

import com.SchoolManagementSystem.System.dto.user.GuardianDto;
import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import com.SchoolManagementSystem.System.entity.enumeration.UserType;
import com.SchoolManagementSystem.System.mapper.user.GuardianMapper;
import com.SchoolManagementSystem.System.entity.user.Guardian;
import com.SchoolManagementSystem.System.repository.user.GuardianRepository;
import com.SchoolManagementSystem.System.repository.user.SecretaryRepository;
import com.SchoolManagementSystem.System.security.AuthUserRepository;
import com.SchoolManagementSystem.System.security.dto.AuthRequestGuardian;
import com.SchoolManagementSystem.System.security.service.AuthUserService;
import com.SchoolManagementSystem.System.service.user.GuardianService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GuardianServiceImpl implements GuardianService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final GuardianRepository repository;

    @Override
    public void save(AuthRequestGuardian authRequestGuardian) {

        if (authUserRepository.findByEmail(authRequestGuardian.email()).isPresent()) {
            log.info("Principal already exists");
            throw new RuntimeException("Email already exists");
        }

        if (repository.findByNationalId(authRequestGuardian.nationalId()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Guardian guardian = new Guardian();

        guardian.setNationalId(authRequestGuardian.nationalId());
        guardian.setFirstName(authRequestGuardian.firstName());
        guardian.setLastName(authRequestGuardian.lastName());
        guardian.setPhone(authRequestGuardian.phone());
        guardian.setAddress(authRequestGuardian.address());
        guardian.setStatus(authRequestGuardian.status());
        guardian.setOccupation(authRequestGuardian.occupation());

        Guardian guardianSaved = repository.save(guardian);

        AuthUser authUser = new AuthUser();
        authUser.setEmail(authRequestGuardian.email());
        authUser.setPassword(passwordEncoder.encode("1234"));
        authUser.setRole(Role.GUARDIAN);
        authUser.setRefId(guardianSaved.getId());

        authUserRepository.save(authUser);
    }

    @Override
    public GuardianDto update(Long id, GuardianDto dto) {
        Guardian guardian = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guardian not found"));

        guardian.setNationalId(dto.nationalId());
        guardian.setFirstName(dto.firstName());
        guardian.setLastName(dto.lastName());
        guardian.setPhone(dto.phone());
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

    //TODO should remove it
    @Override
    public GuardianDto save(GuardianDto dto) {
        return null;
    }
}
