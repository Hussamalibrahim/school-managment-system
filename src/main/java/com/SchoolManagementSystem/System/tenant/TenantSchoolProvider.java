package com.SchoolManagementSystem.System.tenant;


import com.SchoolManagementSystem.System.entity.school.School;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.repository.school.SchoolRepository;
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