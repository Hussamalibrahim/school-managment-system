package com.SchoolManagementSystem.system.service.admin.impl;

import com.SchoolManagementSystem.system.dto.admin.*;
import com.SchoolManagementSystem.system.dto.school.SchoolAdminStatisticsDto;
import com.SchoolManagementSystem.system.dto.school.SchoolStatistics;
import com.SchoolManagementSystem.system.dto.school.UserStatistics;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import com.SchoolManagementSystem.system.entity.enumeration.SchoolRequestStatus;
import com.SchoolManagementSystem.system.entity.school.School;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.repository.auth.AuthUserRepository;
import com.SchoolManagementSystem.system.repository.school.SchoolRepository;
import com.SchoolManagementSystem.system.repository.school.SchoolRequestRepository;
import com.SchoolManagementSystem.system.repository.student.StudentRepository;
import com.SchoolManagementSystem.system.service.admin.AdminStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminStatisticsServiceImpl
        implements AdminStatisticsService {

    private final SchoolRepository schoolRepository;
    private final SchoolRequestRepository schoolRequestRepository;
    private final AuthUserRepository authUserRepository;
    private final StudentRepository studentRepository;


    @Override
    @Transactional(readOnly = true)
    public AdminStatisticsDto getStatistics() {

        long totalSchools = schoolRepository.count();
        long enabledSchools = schoolRepository.countByEnabledTrue();
        long disabledSchools = schoolRepository.countByEnabledFalse();
        long pending = schoolRequestRepository.countByStatus(SchoolRequestStatus.PENDING);
        long approved = schoolRequestRepository.countByStatus(SchoolRequestStatus.APPROVED);
        long rejected = schoolRequestRepository.countByStatus(SchoolRequestStatus.REJECTED);
        long totalUsers = authUserRepository.count();
        long principals = authUserRepository.countByRole(Role.PRINCIPAL);
        long teachers = authUserRepository.countByRole(Role.TEACHER);
        long students = authUserRepository.countByRole(Role.STUDENT);
        long guardians = authUserRepository.countByRole(Role.GUARDIAN);
        long secretaries = authUserRepository.countByRole(Role.SECRETARY);


        return new AdminStatisticsDto(

                new SchoolStatistics(
                        totalSchools,
                        enabledSchools,
                        disabledSchools),

                new RequestStatistics(
                        pending,
                        approved,
                        rejected
                ),

                new UserStatistics(
                        totalUsers,
                        principals,
                        teachers,
                        students,
                        guardians,
                        secretaries)
        );
    }


    @Override
    @Transactional(readOnly = true)
    public List<SchoolAdminStatisticsDto> getSchoolsStatistics() {

        return schoolRepository.findAll()
                .stream()
                .map(this::buildSchoolStatistics)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public SchoolAdminStatisticsDto getSchoolStatistics(Long schoolId) {
        School school = schoolRepository.findById(schoolId)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND));

        return buildSchoolStatistics(school);
    }


    private SchoolAdminStatisticsDto buildSchoolStatistics(School school) {

        Long schoolId = school.getId();
        long users = authUserRepository.countBySchoolId(schoolId);
        long teachers = authUserRepository.countBySchoolIdAndRole(schoolId, Role.TEACHER);
        long guardians = authUserRepository.countBySchoolIdAndRole(schoolId, Role.GUARDIAN);
        long secretaries = authUserRepository.countBySchoolIdAndRole(schoolId, Role.SECRETARY);
        long students = studentRepository.countBySchoolId(schoolId);


        return new SchoolAdminStatisticsDto(
                school.getId(),
                school.getName(),
                school.getCode(),
                school.getEnabled(),
                users,
                students,
                guardians,
                teachers,
                secretaries);
    }
}