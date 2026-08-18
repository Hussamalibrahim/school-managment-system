package com.SchoolManagementSystem.system.service.school.impl;

import com.SchoolManagementSystem.system.dto.request.updateSchoolInfo;
import com.SchoolManagementSystem.system.dto.school.SchoolDto;
import com.SchoolManagementSystem.system.dto.school.request.SchoolRegisterRequest;
import com.SchoolManagementSystem.system.entity.Auth.AuthUser;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import com.SchoolManagementSystem.system.entity.enumeration.SchoolRequestStatus;
import com.SchoolManagementSystem.system.entity.school.SchoolRequest;
import com.SchoolManagementSystem.system.entity.user.Principal;
import com.SchoolManagementSystem.system.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.school.SchoolMapper;
import com.SchoolManagementSystem.system.entity.school.School;
import com.SchoolManagementSystem.system.repository.auth.AuthUserRepository;
import com.SchoolManagementSystem.system.repository.school.SchoolRepository;
import com.SchoolManagementSystem.system.repository.school.SchoolRequestRepository;
import com.SchoolManagementSystem.system.repository.user.PrincipalRepository;
import com.SchoolManagementSystem.system.service.school.SchoolService;
import com.SchoolManagementSystem.system.tenant.TenantContext;
import com.SchoolManagementSystem.system.utils.CodeNameUtil;
import com.SchoolManagementSystem.system.utils.GradeLevelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUserRepository authUserRepository;
    private final PrincipalRepository principalRepository;
    private final SchoolRequestRepository schoolRequestRepository;

    //TODO Need to be removed
    @Override
    public SchoolDto save(SchoolDto dto) {
        return null;
    }

    //TODO Need to be removed
    @Override
    public SchoolDto update(Long id, SchoolDto dto) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public SchoolDto getById(Long id) {

        return SchoolMapper.toDto(schoolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchoolDto> getAll() {
        return schoolRepository.findAll()
                .stream()
                .map(SchoolMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        schoolRepository.deleteById(
                schoolRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND)));
    }

    @Override
    @Transactional
    public SchoolDto update(Long id, updateSchoolInfo dto) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND));

        SchoolMapper.updateEntity(dto, school);

        return SchoolMapper.toDto(schoolRepository.save(school));
    }

    @Override
    @Transactional
    public void register(SchoolRegisterRequest request) {
        if (schoolRepository.existsByName(request.schoolName())) {
            throw new AlreadyExistsException(ErrorCode.SCHOOL_NAME_ALREADY_EXIST);
        }

        School school = new School();

        school.setName(request.schoolName());
        school.setEducationStages(request.educationStages());
        school.setSchoolType(request.schoolType());
        school.setCode(CodeNameUtil.generateCode(request.schoolName()));

        schoolRepository.save(school);

        Principal principal = new Principal();

        principal.setFirstName(request.firstName());
        principal.setLastName(request.lastName());
        principal.setNationalId(request.nationalId());

        principal.setSchool(school);

        principalRepository.save(principal);

        AuthUser authUser = new AuthUser();
        authUser.setEmail(request.email());
        authUser.setPassword(passwordEncoder.encode(request.password()));

        authUser.setRole(Role.PRINCIPAL);
        authUser.setRefId(principal.getId());
        authUser.setSchool(school);
        authUser.setEnabled(false);

        authUserRepository.save(authUser);


        SchoolRequest schoolRequest = new SchoolRequest();

        schoolRequest.setSchool(school);
        schoolRequest.setStatus(SchoolRequestStatus.PENDING);

        schoolRequestRepository.save(schoolRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public SchoolDto findByUrl() {
        Long schoolId = TenantContext.getSchoolId();

        return SchoolMapper.toDto(
                schoolRepository.findById(schoolId)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND)));
    }

    @Override
    public Set<GradeLevel> availableGrades() {
        Long schoolId = TenantContext.getSchoolId();

        return GradeLevelUtil.getByStages(
                schoolRepository.findById(schoolId)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND))
                        .getEducationStages());
    }
}
