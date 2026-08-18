package com.SchoolManagementSystem.system.service.school.impl;

import com.SchoolManagementSystem.system.dto.school.SchoolRequestDto;
import com.SchoolManagementSystem.system.entity.Auth.AuthUser;
import com.SchoolManagementSystem.system.entity.academic.Semester;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import com.SchoolManagementSystem.system.entity.enumeration.SchoolRequestStatus;
import com.SchoolManagementSystem.system.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.system.entity.school.AcademicYear;
import com.SchoolManagementSystem.system.entity.school.School;
import com.SchoolManagementSystem.system.entity.school.SchoolRequest;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.school.SchoolRequestMapper;
import com.SchoolManagementSystem.system.repository.academic.SemesterRepository;
import com.SchoolManagementSystem.system.repository.auth.AuthUserRepository;
import com.SchoolManagementSystem.system.repository.school.AcademicYearRepository;
import com.SchoolManagementSystem.system.repository.school.SchoolRequestRepository;
import com.SchoolManagementSystem.system.service.school.SchoolRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolRequestServiceImpl implements SchoolRequestService {

    private final SchoolRequestRepository schoolRequestRepository;
    private final AuthUserRepository authUserRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SchoolRequestDto> getAll() {

        return schoolRequestRepository.findAll()
                .stream()
                .map(SchoolRequestMapper::toDto)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<SchoolRequestDto> getPending() {

        return schoolRequestRepository
                .findByStatus(SchoolRequestStatus.PENDING)
                .stream()
                .map(SchoolRequestMapper::toDto)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public SchoolRequestDto getById(Long id) {

        SchoolRequest request = findRequest(id);
        return SchoolRequestMapper.toDto(request);
    }


    @Override
    @Transactional
    public void approve(Long id) {

        SchoolRequest request = findRequest(id);

        validatePending(request);
        School school = request.getSchool();
        school.setEnabled(true);

        AuthUser principal = authUserRepository.findBySchoolIdAndRole(school.getId(), Role.PRINCIPAL)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        principal.setEnabled(true);
        request.setStatus(SchoolRequestStatus.APPROVED);
        request.setReviewedAt(LocalDateTime.now());
        request.setRejectionReason(null);

        schoolRequestRepository.save(request);
    }


    @Override
    @Transactional
    public void reject(Long id, String reason) {

        SchoolRequest request = findRequest(id);

        validatePending(request);
        School school = request.getSchool();
        school.setEnabled(false);

        AuthUser principal = authUserRepository.findBySchoolIdAndRole(school.getId(), Role.PRINCIPAL)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        principal.setEnabled(false);

        request.setStatus(SchoolRequestStatus.REJECTED);
        request.setRejectionReason(reason);
        request.setReviewedAt(LocalDateTime.now());

        schoolRequestRepository.save(request);
    }


    private SchoolRequest findRequest(Long id) {

        return schoolRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SCHOOL_REQUEST_NOT_FOUND));
    }


    private void validatePending(
            SchoolRequest request) {

        if (request.getStatus() != SchoolRequestStatus.PENDING) {
            throw new ValidationException(ErrorCode.SCHOOL_REQUEST_ALREADY_REVIEWED);
        }
    }
}