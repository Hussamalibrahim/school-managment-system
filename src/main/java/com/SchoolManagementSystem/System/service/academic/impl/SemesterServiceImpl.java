package com.SchoolManagementSystem.System.service.academic.impl;

import com.SchoolManagementSystem.System.dto.academic.SemesterDto;
import com.SchoolManagementSystem.System.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.mapper.academic.SemesterMapper;
import com.SchoolManagementSystem.System.entity.academic.Semester;
import com.SchoolManagementSystem.System.repository.academic.SemesterRepository;
import com.SchoolManagementSystem.System.service.academic.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;

    @Override
    @Transactional
    public SemesterDto save(SemesterDto dto) {
        if (semesterRepository.existsBySemesterName(dto.semesterName()))
            throw new AlreadyExistsException(ErrorCode.SEMESTER_ALREADY_EXISTS);
        return SemesterMapper.toDto(
                semesterRepository.save(SemesterMapper
                        .toEntity(dto)));
    }
    @Transactional
    @Override
    public SemesterDto update(Long id, SemesterDto dto) {
        Semester semester = semesterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));

        SemesterMapper.updateEntity(semester, dto);

        return SemesterMapper.toDto(semesterRepository.save(semester));
    }

    @Override
    @Transactional(readOnly = true)
    public SemesterDto getById(Long id) {
        return semesterRepository.findById(id)
                .map(SemesterMapper::toDto)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SemesterDto> getAll() {
        return semesterRepository.findAll()
                .stream()
                .map(SemesterMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        semesterRepository.deleteById(
                semesterRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.SEMESTER_NOT_FOUND)));
    }
}
