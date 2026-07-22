package com.SchoolManagementSystem.System.service.user.impl;


import com.SchoolManagementSystem.System.dto.user.SecretaryDto;
import com.SchoolManagementSystem.System.entity.user.Secretary;
import com.SchoolManagementSystem.System.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.mapper.user.SecretaryMapper;
import com.SchoolManagementSystem.System.repository.user.SecretaryRepository;
import com.SchoolManagementSystem.System.service.NationalIdValidator;
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
    private final NationalIdValidator nationalIdValidator;

    // remove later if creation only through Principal
    @Override
    @Transactional
    public SecretaryDto save(SecretaryDto dto) {

        if (nationalIdValidator.validate(dto.nationalId())) {
            throw new AlreadyExistsException(ErrorCode.NATIONAL_ID_ALREADY_EXISTS);
        }

        Secretary secretary = SecretaryMapper.toEntity(dto);

        return SecretaryMapper.toDto(repository.save(secretary));
    }

    @Override
    @Transactional
    public SecretaryDto update(Long id, SecretaryDto dto) {
        Secretary secretary =
                repository.findById(id)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.SECRETARY_NOT_FOUND));

        if (!secretary.getNationalId()
                .equals(dto.nationalId()) && nationalIdValidator.validate(dto.nationalId())) {

            throw new AlreadyExistsException(
                    ErrorCode.NATIONAL_ID_ALREADY_EXISTS
            );
        }

        SecretaryMapper.updateEntity(secretary, dto);

        return SecretaryMapper.toDto(
                repository.save(secretary)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public SecretaryDto getById(Long id) {

        return repository.findById(id)
                .map(SecretaryMapper::toDto)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SECRETARY_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SecretaryDto> getAll() {

        return repository.findAll()
                .stream()
                .map(SecretaryMapper::toDto)
                .toList();
    }


    @Override
    @Transactional
    public void delete(Long id) {

        repository.delete(repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SECRETARY_NOT_FOUND)));
    }

}