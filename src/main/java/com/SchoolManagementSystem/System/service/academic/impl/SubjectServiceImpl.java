package com.SchoolManagementSystem.System.service.academic.impl;

import com.SchoolManagementSystem.System.dto.academic.SubjectDto;
import com.SchoolManagementSystem.System.dto.academic.request.SubjectCreateRequest;
import com.SchoolManagementSystem.System.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.System.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.System.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.mapper.academic.SubjectMapper;
import com.SchoolManagementSystem.System.entity.academic.Subject;
import com.SchoolManagementSystem.System.repository.academic.SubjectRepository;
import com.SchoolManagementSystem.System.service.academic.SubjectService;
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