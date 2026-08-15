package com.SchoolManagementSystem.system.service.academic.impl;

import com.SchoolManagementSystem.system.dto.academic.SubjectDto;
import com.SchoolManagementSystem.system.dto.academic.request.SubjectCreateRequest;
import com.SchoolManagementSystem.system.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.system.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.academic.SubjectMapper;
import com.SchoolManagementSystem.system.entity.academic.Subject;
import com.SchoolManagementSystem.system.repository.academic.SubjectRepository;
import com.SchoolManagementSystem.system.service.academic.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl
        implements SubjectService {
    private final SubjectRepository subjectRepository;

    @Override
    @Transactional
    public SubjectDto save(SubjectDto dto) {
        return null;
    }

    @Override
    @Transactional
    public SubjectDto update(Long id, SubjectDto dto) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SUBJECT_NOT_FOUND));

        SubjectMapper.updateEntity(subject, dto);

        return SubjectMapper.toDto(
                subjectRepository.save(subject)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectDto getById(Long id) {
        return SubjectMapper.toDto(
                subjectRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.SUBJECT_NOT_FOUND)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectDto> getAll() {
        return subjectRepository.findAll().stream()
                .map(SubjectMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public void delete(Long id) {
        subjectRepository.delete(
                subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SUBJECT_NOT_FOUND)));
    }

    @Override
    @Transactional
    public SubjectDto save(SubjectCreateRequest dto) {

        if (subjectRepository.existsByName(dto.name()))
            throw new AlreadyExistsException(ErrorCode.SUBJECT_ALREADY_EXISTS);

        return SubjectMapper.toDto(subjectRepository.save(SubjectMapper.fromCreateRequest(dto)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectDto> getBySemester(SemesterName semesterName) {
        return subjectRepository.findSubjectBySemesterName(semesterName)
                .stream().map(SubjectMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectDto> getByGrade(GradeLevel gradeLevel) {
        return subjectRepository.findSubjectByGradeLevel(gradeLevel)
                .stream().map(SubjectMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectDto> getSubjectByGradeAndSemester(
            GradeLevel gradeLevel,
            SemesterName semesterName) {

        return subjectRepository
                .findByGradeLevelAndSemesterName(gradeLevel, semesterName)
                .stream()
                .map(SubjectMapper::toDto)
                .toList();
    }

}