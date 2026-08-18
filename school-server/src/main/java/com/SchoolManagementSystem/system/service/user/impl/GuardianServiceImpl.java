package com.SchoolManagementSystem.system.service.user.impl;

import com.SchoolManagementSystem.system.dto.student.StudentDto;
import com.SchoolManagementSystem.system.dto.user.GuardianDto;
import com.SchoolManagementSystem.system.dto.user.request.AuthRequestGuardian;
import com.SchoolManagementSystem.system.entity.Auth.AuthUser;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import com.SchoolManagementSystem.system.entity.student.StudentGuardian;
import com.SchoolManagementSystem.system.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.auth.AuthUserMapper;
import com.SchoolManagementSystem.system.mapper.student.StudentGuardianMapper;
import com.SchoolManagementSystem.system.mapper.student.StudentMapper;
import com.SchoolManagementSystem.system.mapper.user.GuardianMapper;
import com.SchoolManagementSystem.system.entity.user.Guardian;
import com.SchoolManagementSystem.system.repository.auth.AuthUserRepository;
import com.SchoolManagementSystem.system.repository.student.StudentGuardianRepository;
import com.SchoolManagementSystem.system.repository.user.GuardianRepository;
import com.SchoolManagementSystem.system.service.NationalIdValidator;
import com.SchoolManagementSystem.system.service.user.GuardianService;
import com.SchoolManagementSystem.system.tenant.TenantContext;
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

    @Override
    public List<StudentDto> getStudentGuardian(Long refId) {

        return studentGuardianRepository.findByGuardianId(refId).stream()
                .map(StudentGuardian::getStudent)
                .map(StudentMapper::toDto)
                .toList();
    }
}
