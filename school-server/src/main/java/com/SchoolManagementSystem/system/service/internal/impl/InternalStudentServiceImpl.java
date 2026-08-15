package com.SchoolManagementSystem.system.service.internal.impl;

import com.SchoolManagementSystem.system.dto.internal.InternalStudentDto;
import com.SchoolManagementSystem.system.entity.student.Student;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.repository.student.StudentRepository;
import com.SchoolManagementSystem.system.service.internal.InternalStudentService;
import com.SchoolManagementSystem.system.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InternalStudentServiceImpl
        implements InternalStudentService {

    private final StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public InternalStudentDto getStudent(Long studentId) {

        Long schoolId = TenantContext.getSchoolId();

        if (schoolId == null) {
            throw new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND);
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new NotFoundException(
                                ErrorCode.STUDENT_NOT_FOUND
                        ));

        return new InternalStudentDto(
                student.getId(),
                student.getFirstName() + " " + student.getLastName(),
                schoolId
        );
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long studentId) {

        Long schoolId = TenantContext.getSchoolId();

        if (schoolId == null) {
            return false;
        }

        return studentRepository.findById(studentId).isPresent();
    }
}