package com.SchoolManagementSystem.System.service.academic.impl;

import com.SchoolManagementSystem.System.dto.academic.SubjectDto;
import com.SchoolManagementSystem.System.dto.academic.request.SubjectCreateRequest;
import com.SchoolManagementSystem.System.dto.academic.request.SubjectNameDto;
import com.SchoolManagementSystem.System.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.System.entity.enumeration.Semester;
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
@Transactional
@RequiredArgsConstructor
public class SubjectServiceImpl
        implements SubjectService {
    private final SubjectRepository subjectRepository;

    @Override
    public SubjectDto save(SubjectDto dto) {
        return null;
    }

    @Override
    public SubjectDto update(Long id, SubjectDto dto) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guardian not found"));

        subject.setName(dto.name());
        subject.setGradeLevel(dto.gradeLevel());
        subject.setSemester(dto.semester());
        return SubjectMapper.toDto(subjectRepository.save(subject));
    }

    @Override
    public SubjectDto getById(Long id) {
        return SubjectMapper.toDto(
                subjectRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id)));
    }

    @Override
    public List<SubjectDto> getAll() {
        return subjectRepository.findAll().stream()
                .map(SubjectMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guardian not found"));
        subjectRepository.delete(subject);
    }
    @Override
    public SubjectDto save(SubjectCreateRequest dto){

        if (subjectRepository.findSubjectByName(dto.name()).isPresent())
            throw new RuntimeException("Subject with name " + dto.name() + " already exists");

        Subject subject = new Subject();
        subject.setName(dto.name());
        subject.setGradeLevel(dto.gradeLevel());
        log.info("Saving subject with name: " + dto.name() + ", grade level: " + dto.gradeLevel() + ", semester: " + dto.semester());
        log.info(dto.gradeLevel().getClass().getTypeName());
        subject.setSemester(dto.semester());
        return SubjectMapper.toDto(subjectRepository.save(subject));
    }
    @Override
    public List<SubjectDto> getBySemester(Semester semester){
        return subjectRepository.findSubjectBySemester(semester)
                .stream().map(SubjectMapper::toDto)
                .toList();
    }

    @Override
    public List<SubjectDto> getByGrade(GradeLevel gradeLevel){
        return subjectRepository.findSubjectByGradeLevel(gradeLevel)
                .stream().map(SubjectMapper::toDto)
                .toList();
    }
    @Override
    public List<SubjectDto> getSubjectByGradeAndSemester(
            GradeLevel gradeLevel,
            Semester semester) {

        return subjectRepository
                .findByGradeLevelAndSemester(gradeLevel, semester)
                .stream()
                .map(SubjectMapper::toDto)
                .toList();
    }

}