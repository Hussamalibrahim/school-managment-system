package com.SchoolManagementSystem.System.service.school.impl;

import com.SchoolManagementSystem.System.dto.request.DefineSchool;
import com.SchoolManagementSystem.System.dto.request.updateSchoolInfo;
import com.SchoolManagementSystem.System.dto.school.SchoolDto;
import com.SchoolManagementSystem.System.dto.school.request.SchoolRegisterRequest;
import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.entity.enumeration.Role;
import com.SchoolManagementSystem.System.entity.user.Principal;
import com.SchoolManagementSystem.System.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.mapper.school.SchoolMapper;
import com.SchoolManagementSystem.System.entity.school.School;
import com.SchoolManagementSystem.System.repository.school.SchoolRepository;
import com.SchoolManagementSystem.System.repository.user.PrincipalRepository;
import com.SchoolManagementSystem.System.security.AuthUserRepository;
import com.SchoolManagementSystem.System.service.school.SchoolService;
import com.SchoolManagementSystem.System.utils.CodeNameUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUserRepository authUserRepository;
    private final PrincipalRepository principalRepository;

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
    public void defineSchool(DefineSchool defineSchool) {
        School school = new School();

        SchoolMapper.fromDefineSchool(defineSchool, school);

        schoolRepository.save(school);
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
        if (schoolRepository.existsByName(request.schoolName())){
            throw new AlreadyExistsException(ErrorCode.SCHOOL_NAME_ALREADY_EXIST);
        }

        School school = new School();

        school.setName(request.schoolName());
        school.setEducationStages(request.educationStages());
        school.setSchoolType(request.schoolType());
        school.setCode(CodeNameUtil.generateCode(request.schoolName()));

        schoolRepository.save(school);

        // 2- Create Principal

        Principal principal = new Principal();

        principal.setFirstName(request.firstName());
        principal.setLastName(request.lastName());
        principal.setNationalId(request.nationalId());

        principal.setSchool(school);

        principalRepository.save(principal);

        // 3- Create AuthUser

        AuthUser authUser = new AuthUser();
        authUser.setEmail(request.email());
        authUser.setPassword(passwordEncoder.encode(request.password()));

        authUser.setRole(Role.PRINCIPAL);
        authUser.setRefId(principal.getId());
        authUser.setSchool(school);
        authUser.setEnabled(false);

        authUserRepository.save(authUser);

    }

    @Override
    @Transactional(readOnly = true)
    public School findByCode(String code){

        return schoolRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND));
    }
}
