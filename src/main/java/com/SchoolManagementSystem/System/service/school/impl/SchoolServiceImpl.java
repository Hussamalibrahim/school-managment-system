package com.SchoolManagementSystem.System.service.school.impl;

import com.SchoolManagementSystem.System.dto.request.DefineSchool;
import com.SchoolManagementSystem.System.dto.request.updateSchoolInfo;
import com.SchoolManagementSystem.System.dto.school.SchoolDto;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.mapper.school.SchoolMapper;
import com.SchoolManagementSystem.System.entity.school.School;
import com.SchoolManagementSystem.System.repository.school.SchoolRepository;
import com.SchoolManagementSystem.System.service.school.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository repository;

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

        return SchoolMapper.toDto(repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchoolDto> getAll() {
        return repository.findAll()
                .stream()
                .map(SchoolMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(
                repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND)));
    }

    @Override
    @Transactional
    public void defineSchool(DefineSchool defineSchool) {
        School school = new School();

        SchoolMapper.fromDefineSchool(defineSchool, school);

        repository.save(school);
    }

    @Override
    @Transactional
    public SchoolDto update(Long id, updateSchoolInfo dto) {
        School school = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND));

        SchoolMapper.updateEntity(dto, school);

        return SchoolMapper.toDto(repository.save(school));
    }
}
