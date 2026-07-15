package com.SchoolManagementSystem.System.service.school.impl;

import com.SchoolManagementSystem.System.dto.request.DefineSchool;
import com.SchoolManagementSystem.System.dto.request.updateSchoolInfo;
import com.SchoolManagementSystem.System.dto.school.SchoolDto;
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
@Transactional
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository repository;

    @Override
    public SchoolDto save(SchoolDto dto) {
        School school = SchoolMapper.toEntity(dto);
        school = repository.save(school);
        return SchoolMapper.toDto(school);
    }

    @Override
    public SchoolDto update(Long id, SchoolDto dto) {
        School school = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));

        school.setName(dto.name());
        school.setSchoolType(dto.schoolType());
        school.setAddress(dto.address());
        school.setPhone(dto.phone());
        school.setLogoPath(dto.logoPath());
        school.setEducationStages(dto.educationStages());

        school = repository.save(school);
        return SchoolMapper.toDto(school);
    }

    @Override
    @Transactional(readOnly = true)
    public SchoolDto getById(Long id) {

        School school = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));

        school.getEducationStages().size();

        return SchoolMapper.toDto(school);
    }

    @Override
    public List<SchoolDto> getAll() {
        return repository.findAll()
                .stream()
                .map(SchoolMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void defineSchool(DefineSchool defineSchool) {
        School school = new School();
        school.setName(defineSchool.name());

        school.setEducationStages(defineSchool.educationStages());
        school.setSchoolType(defineSchool.schoolType());

        repository.save(school);
    }

    @Override
    public SchoolDto update(Long id, updateSchoolInfo dto) {
        School school = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));

        school.setName(dto.name());
        school.setAddress(dto.address());
        school.setPhone(dto.phone());
        school.setLogoPath(dto.logoPath());

        school = repository.save(school);
        return SchoolMapper.toDto(school);
    }
}
