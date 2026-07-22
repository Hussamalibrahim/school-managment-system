package com.SchoolManagementSystem.System.service;

import com.SchoolManagementSystem.System.entity.enumeration.UserType;
import com.SchoolManagementSystem.System.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.repository.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class NationalIdValidator {


    private final TeacherRepository teacherRepository;
    private final SecretaryRepository secretaryRepository;
    private final LibrarianRepository librarianRepository;
    private final PrincipalRepository principalRepository;
    private final GuardianRepository guardianRepository;


    public boolean validate(String nationalId) {

        return teacherRepository.existsByNationalId(nationalId)
                || secretaryRepository.existsByNationalId(nationalId)
                || librarianRepository.existsByNationalId(nationalId)
                || principalRepository.existsByNationalId(nationalId)
                || guardianRepository.existsByNationalId(nationalId);
    }
}