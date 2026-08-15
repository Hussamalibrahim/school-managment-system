package com.SchoolManagementSystem.system.tenant;


import com.SchoolManagementSystem.system.entity.school.School;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.repository.school.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TenantSchoolProvider {
    private final SchoolRepository repository;

    public School getCurrentSchool(){

        Long id = TenantContext.getSchoolId();

        if(id == null){
            throw new ValidationException(ErrorCode.NO_TENANT_SELECTED);
        }
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND));
    }
}