package com.SchoolManagementSystem.system.service.user.impl;


import com.SchoolManagementSystem.system.dto.user.SecretaryDto;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import com.SchoolManagementSystem.system.entity.user.Secretary;
import com.SchoolManagementSystem.system.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.user.SecretaryMapper;
import com.SchoolManagementSystem.system.repository.auth.AuthUserRepository;
import com.SchoolManagementSystem.system.repository.user.SecretaryRepository;
import com.SchoolManagementSystem.system.service.NationalIdValidator;
import com.SchoolManagementSystem.system.service.user.SecretaryService;

import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class SecretaryServiceImpl implements SecretaryService {

    private final AuthUserRepository authUserRepository;
    private final SecretaryRepository secretaryRepository;
    private final NationalIdValidator nationalIdValidator;

    // remove later if creation only through Principal
    @Override
    @Transactional
    public SecretaryDto save(SecretaryDto dto) {

        if (nationalIdValidator.validate(dto.nationalId())) {
            throw new AlreadyExistsException(ErrorCode.NATIONAL_ID_ALREADY_EXISTS);
        }

        Secretary secretary = SecretaryMapper.toEntity(dto);

        return SecretaryMapper.toDto(secretaryRepository.save(secretary));
    }

    @Override
    @Transactional
    public SecretaryDto update(Long id, SecretaryDto dto) {
        Secretary secretary =
                secretaryRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.SECRETARY_NOT_FOUND));

        if (!secretary.getNationalId()
                .equals(dto.nationalId()) && nationalIdValidator.validate(dto.nationalId())) {

            throw new AlreadyExistsException(
                    ErrorCode.NATIONAL_ID_ALREADY_EXISTS
            );
        }

        SecretaryMapper.updateEntity(secretary, dto);

        return SecretaryMapper.toDto(
                secretaryRepository.save(secretary)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public SecretaryDto getById(Long id) {

        return secretaryRepository.findById(id)
                .map(SecretaryMapper::toDto)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SECRETARY_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SecretaryDto> getAll() {

        return secretaryRepository.findAll()
                .stream()
                .map(SecretaryMapper::toDto)
                .toList();
    }


    @Override
    @Transactional
    public void delete(Long id) {

        Secretary secretary = secretaryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SECRETARY_NOT_FOUND));

        authUserRepository.deleteByRefIdAndRole(id, Role.SECRETARY);
        secretaryRepository.delete(secretary);
    }

}