package com.SchoolManagementSystem.System.service.academic.impl;

import com.SchoolManagementSystem.System.dto.academic.AssessmentDto;
import com.SchoolManagementSystem.System.mapper.academic.AssessmentMapper;
import com.SchoolManagementSystem.System.entity.academic.Assessment;
import com.SchoolManagementSystem.System.repository.academic.AssessmentRepository;
import com.SchoolManagementSystem.System.service.academic.AssessmentService;
import com.SchoolManagementSystem.System.service.impl.BaseCrudServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AssessmentServiceImpl
        extends BaseCrudServiceImpl<Assessment, AssessmentDto>
        implements AssessmentService {

    public AssessmentServiceImpl(AssessmentRepository repository) {
        super(repository);
    }

    @Override
    protected AssessmentDto toDto(Assessment entity) {
        return AssessmentMapper.toDto(entity);
    }

    @Override
    protected Assessment toEntity(AssessmentDto dto) {
        return AssessmentMapper.toEntity(dto);
    }
}