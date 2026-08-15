package com.SchoolManagementSystem.system.service.tenant;

import com.SchoolManagementSystem.system.entity.school.School;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.repository.school.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final SchoolRepository schoolRepository;

    @Transactional(readOnly = true)
    public School resolveSchool(String schoolCode) {

        return schoolRepository.findByCode(schoolCode)
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND)
                );
    }
}