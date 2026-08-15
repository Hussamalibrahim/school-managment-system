package com.SchoolManagementSystem.System.service.user.impl;

import com.SchoolManagementSystem.System.dto.user.GuardianDto;
import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import com.SchoolManagementSystem.System.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.mapper.user.GuardianMapper;
import com.SchoolManagementSystem.System.entity.user.Guardian;
import com.SchoolManagementSystem.System.repository.user.GuardianRepository;
import com.SchoolManagementSystem.System.security.AuthUserRepository;
import com.SchoolManagementSystem.System.security.dto.AuthRequestGuardian;
import com.SchoolManagementSystem.System.security.mapper.AuthUserMapper;
import com.SchoolManagementSystem.System.service.NationalIdValidator;
import com.SchoolManagementSystem.System.service.user.GuardianService;
import com.SchoolManagementSystem.System.tenant.TenantContext;
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
    private final NationalIdValidator nationalIdValidator;

    @Override
    public void save(AuthRequestGuardian authRequestGuardian) {
        Long schoolId = TenantContext.getSchoolId();
        if (schoolId == null) {
            throw new ValidationException(ErrorCode.SCHOOL_NOT_FOUND);
        }
        if (authUserRepository.findByEmailAndSchoolId(authRequestGuardian.email(), schoolId).isPresent()) {
            throw new AlreadyExistsException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        if (repository.findByNationalId(authRequestGuardian.nationalId()).isPresent()) {
            throw new AlreadyExistsException(ErrorCode.NATIONAL_ID_ALREADY_EXISTS);
        }


        Guardian guardian = repository.save(
                GuardianMapper.fromAuthRequestGuardian(authRequestGuardian)
        );

        AuthUser authUser = AuthUserMapper.fromRegisterRequest(authRequestGuardian.email(),
                passwordEncoder.encode("1234"),
                guardian.getId(),
                Role.GUARDIAN);

        authUserRepository.save(authUser);
    }

    @Override
    public GuardianDto update(Long id, GuardianDto dto) {


        Guardian guardian = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GUARDIAN_NOT_FOUND));


        nationalIdValidator.validate(dto.nationalId());

        GuardianMapper.updateEntity(guardian, dto);


        return GuardianMapper.toDto(
                repository.save(guardian));
    }

    @Override
    public GuardianDto getById(Long id) {
        return repository.findById(id)
                .map(GuardianMapper::toDto)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GUARDIAN_NOT_FOUND));
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
        repository.delete(repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GUARDIAN_NOT_FOUND)));
    }

    //TODO should remove it
    @Override
    public GuardianDto save(GuardianDto dto) {
        return null;
    }
}
