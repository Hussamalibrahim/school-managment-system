package com.SchoolManagementSystem.System.service.academic.impl;

import com.SchoolManagementSystem.System.dto.academic.AssessmentResultDto;
import com.SchoolManagementSystem.System.mapper.academic.AssessmentResultMapper;
import com.SchoolManagementSystem.System.entity.academic.AssessmentResult;
import com.SchoolManagementSystem.System.repository.academic.AssessmentResultRepository;
import com.SchoolManagementSystem.System.service.academic.AssessmentResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssessmentResultServiceImpl implements AssessmentResultService {

    private final AssessmentResultRepository repository;

    @Override
    public AssessmentResultDto save(AssessmentResultDto dto) {
        AssessmentResult result = AssessmentResultMapper.toEntity(dto);
        result = repository.save(result);
        return AssessmentResultMapper.toDto(result);
    }

    @Override
    public AssessmentResultDto update(Long id, AssessmentResultDto dto) {
        AssessmentResult result = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("AssessmentResult not found"));

        result.setScore(dto.score());
        result.setNotes(dto.notes());

        result = repository.save(result);
        return AssessmentResultMapper.toDto(result);
    }

    @Override
    public AssessmentResultDto getById(Long id) {
        return repository.findById(id)
                .map(AssessmentResultMapper::toDto)
                .orElseThrow(() -> new RuntimeException("AssessmentResult not found"));
    }

    @Override
    public List<AssessmentResultDto> getAll() {
        return repository.findAll()
                .stream()
                .map(AssessmentResultMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}