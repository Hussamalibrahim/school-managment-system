package com.SchoolManagementSystem.System.service.user.impl;

import com.SchoolManagementSystem.System.dto.user.GuardianDto;
import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import com.SchoolManagementSystem.System.entity.student.StudentGuardian;
import com.SchoolManagementSystem.System.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.mapper.user.GuardianMapper;
import com.SchoolManagementSystem.System.entity.user.Guardian;
import com.SchoolManagementSystem.System.repository.student.StudentGuardianRepository;
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
    private final GuardianRepository guardianRepository;
    private final StudentGuardianRepository studentGuardianRepository;
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

        if (guardianRepository.findByNationalId(authRequestGuardian.nationalId()).isPresent()) {
            throw new AlreadyExistsException(ErrorCode.NATIONAL_ID_ALREADY_EXISTS);
        }


        Guardian guardian = guardianRepository.save(
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


        Guardian guardian = guardianRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GUARDIAN_NOT_FOUND));


        nationalIdValidator.validate(dto.nationalId());

        GuardianMapper.updateEntity(guardian, dto);


        return GuardianMapper.toDto(
                guardianRepository.save(guardian));
    }

    @Override
    public GuardianDto getById(Long id) {
        log.info(id.toString());
        log.info(guardianRepository.findById(id).toString());
        return guardianRepository.findById(id)
                .map(GuardianMapper::toDto)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GUARDIAN_NOT_FOUND));
    }

    @Override
    public List<GuardianDto> getAll() {
        return guardianRepository.findAll()
                .stream()
                .map(GuardianMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Guardian guardian = guardianRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GUARDIAN_NOT_FOUND));

        List<StudentGuardian> relations =
                studentGuardianRepository.findByGuardianId(id);

        if (relations.stream().anyMatch(StudentGuardian::getPrimaryGuardian)) {
            throw new ValidationException(ErrorCode.CANT_DELETE_PRIMARY_GUARDIAN);
        }
        authUserRepository.deleteByRefIdAndRole(id, Role.GUARDIAN);
        studentGuardianRepository.deleteAll(relations);
        guardianRepository.delete(guardian);
    }

    //TODO should remove it
    @Override
    public GuardianDto save(GuardianDto dto) {
        return null;
    }
}
