package com.SchoolManagementSystem.System.service.academic.impl;

import com.SchoolManagementSystem.System.dto.academic.SubjectDto;
import com.SchoolManagementSystem.System.mapper.academic.SubjectMapper;
import com.SchoolManagementSystem.System.entity.academic.Subject;
import com.SchoolManagementSystem.System.repository.academic.SubjectRepository;
import com.SchoolManagementSystem.System.service.academic.SubjectService;
import com.SchoolManagementSystem.System.service.impl.BaseCrudServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubjectServiceImpl
        extends BaseCrudServiceImpl<Subject, SubjectDto>
        implements SubjectService {

    public SubjectServiceImpl(SubjectRepository repository) {
        super(repository);
    }

    @Override
    protected SubjectDto toDto(Subject entity) {
        return SubjectMapper.toDto(entity);
    }

    @Override
    protected Subject toEntity(SubjectDto dto) {
        return SubjectMapper.toEntity(dto);
    }
}