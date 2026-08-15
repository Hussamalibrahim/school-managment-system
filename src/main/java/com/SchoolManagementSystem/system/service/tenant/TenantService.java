package com.SchoolManagementSystem.System.service.tenant;

import com.SchoolManagementSystem.System.entity.school.School;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.repository.school.SchoolRepository;
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