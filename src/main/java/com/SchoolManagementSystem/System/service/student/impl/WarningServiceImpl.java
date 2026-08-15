package com.SchoolManagementSystem.System.service.student.impl;

import com.SchoolManagementSystem.System.dto.student.WarningDto;
import com.SchoolManagementSystem.System.mapper.student.WarningMapper;
import com.SchoolManagementSystem.System.entity.student.Warning;
import com.SchoolManagementSystem.System.repository.student.WarningRepository;
import com.SchoolManagementSystem.System.service.student.WarningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.SchoolManagementSystem.System.entity.student.Student;
import com.SchoolManagementSystem.System.repository.student.StudentRepository;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional
public class WarningServiceImpl implements WarningService {

    private final WarningRepository repository;
    private final StudentRepository studentRepository;

    @Override
    public WarningDto save(WarningDto dto) {
        Warning warning = WarningMapper.toEntity(dto);
        if (dto.studentId() != null) {
            Student student = studentRepository.findById(dto.studentId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.STUDENT_NOT_FOUND));
            warning.setStudent(student);
        }
        warning = repository.save(warning);
        return WarningMapper.toDto(warning);
    }

    @Override
    public WarningDto update(Long id, WarningDto dto) {
        Warning warning = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warning not found"));

        warning.setWarningDate(dto.warningDate());
        warning.setReason(dto.reason());

        warning = repository.save(warning);
        return WarningMapper.toDto(warning);
    }

    @Override
    public WarningDto getById(Long id) {
        return repository.findById(id)
                .map(WarningMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Warning not found"));
    }

    @Override
    public List<WarningDto> getAll() {
        return repository.findAll()
                .stream()
                .map(WarningMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarningDto> getWarningsByStudentId(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new NotFoundException(ErrorCode.STUDENT_NOT_FOUND);
        }
        return repository.findByStudentId(studentId)
                .stream()
                .map(WarningMapper::toDto)
                .toList();
    }
}
