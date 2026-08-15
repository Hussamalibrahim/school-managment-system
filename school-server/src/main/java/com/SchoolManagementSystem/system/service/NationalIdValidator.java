package com.SchoolManagementSystem.system.service;

import com.SchoolManagementSystem.system.repository.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


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