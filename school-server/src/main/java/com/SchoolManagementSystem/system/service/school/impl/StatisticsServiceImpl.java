package com.SchoolManagementSystem.system.service.school.impl;

import com.SchoolManagementSystem.system.dto.school.SchoolAdminStatisticsDto;
import com.SchoolManagementSystem.system.dto.school.SchoolStatisticsDto;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import com.SchoolManagementSystem.system.entity.school.School;
import com.SchoolManagementSystem.system.repository.auth.AuthUserRepository;
import com.SchoolManagementSystem.system.repository.school.SchoolRepository;
import com.SchoolManagementSystem.system.repository.school.SchoolRequestRepository;
import com.SchoolManagementSystem.system.repository.student.StudentRepository;
import com.SchoolManagementSystem.system.service.school.StatisticsService;
import com.SchoolManagementSystem.system.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final AuthUserRepository authUserRepository;
    private final StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public SchoolStatisticsDto getSchoolsStatistics() {
        Long schoolId = TenantContext.getSchoolId();

        long users = authUserRepository.countBySchoolId(schoolId);
        long teachers = authUserRepository.countBySchoolIdAndRole(schoolId, Role.TEACHER);
        long guardians = authUserRepository.countBySchoolIdAndRole(schoolId, Role.GUARDIAN);
        long secretaries = authUserRepository.countBySchoolIdAndRole(schoolId, Role.SECRETARY);
        long students = studentRepository.countBySchoolId(schoolId);


        return new SchoolStatisticsDto(
                users,
                students,
                guardians,
                teachers,
                secretaries);
    }
}
