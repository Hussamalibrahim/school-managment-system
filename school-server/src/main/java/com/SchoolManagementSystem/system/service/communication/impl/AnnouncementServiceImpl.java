package com.SchoolManagementSystem.system.service.communication.impl;

import com.SchoolManagementSystem.system.dto.communication.AnnouncementDto;
import com.SchoolManagementSystem.system.dto.communication.request.AnnouncementRequest;
import com.SchoolManagementSystem.system.entity.Auth.AuthUser;
import com.SchoolManagementSystem.system.entity.communication.Announcement;
import com.SchoolManagementSystem.system.entity.communication.AnnouncementTarget;
import com.SchoolManagementSystem.system.entity.school.School;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.communication.AnnouncementMapper;
import com.SchoolManagementSystem.system.repository.auth.AuthUserRepository;
import com.SchoolManagementSystem.system.repository.communication.AnnouncementRepository;
import com.SchoolManagementSystem.system.repository.communication.AnnouncementTargetRepository;
import com.SchoolManagementSystem.system.repository.school.SchoolRepository;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.communication.AnnouncementService;
import com.SchoolManagementSystem.system.service.communication.AttendanceNotificationService;
import com.SchoolManagementSystem.system.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementTargetRepository targetRepository;
    private final SchoolRepository schoolRepository;
    private final AnnouncementNotificationService announcementNotificationService;
    private final AuthUserRepository authUserRepository;


    @Override
    @Transactional
    public AnnouncementDto create(UserPrincipal principal, AnnouncementRequest request) {
        Long schoolId = TenantContext.getSchoolId();

        if (schoolId == null) {
            throw new ValidationException(ErrorCode.SCHOOL_NOT_FOUND);
        }

        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND));

        String schoolCode = school.getCode();

        AuthUser user = authUserRepository
                .findByRefIdAndRoleAndSchoolId(
                        principal.getRefId(),
                        principal.getRole(),
                        schoolId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        validateTarget(request);

        Announcement announcement = new Announcement();

        AnnouncementMapper.requestToEntity(announcement, request);

        announcement.setCreatedBy(user);
        announcement.setActive(true);

        announcement = announcementRepository.save(announcement);

        AnnouncementTarget target = new AnnouncementTarget();

        target.setAnnouncement(announcement);
        target.setType(request.targetType());
        target.setTargetRole(request.targetRole());
        target.setTargetId(request.targetId());

        target = targetRepository.save(target);

        announcementNotificationService.send(
                announcement,
                target,
                schoolCode);

        return AnnouncementMapper.toDto(announcement, target);
    }


    @Override
    @Transactional(readOnly = true)
    public AnnouncementDto getById(Long id) {

        Announcement announcement = findAnnouncement(id);

        AnnouncementTarget target = targetRepository.findAll()
                .stream()
                .filter(t ->
                        t.getAnnouncement()
                                .getId()
                                .equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ErrorCode.ANNOUNCEMENT_NOT_FOUND));

        return AnnouncementMapper.toDto(announcement, target);
    }


    @Override
    @Transactional(readOnly = true)
    public List<AnnouncementDto> getAll() {

        Long schoolId = TenantContext.getSchoolId();

        return announcementRepository
                .findBySchoolIdAndActiveTrueOrderByCreatedAtDesc(schoolId)
                .stream()
                .map(announcement -> {
                    AnnouncementTarget target = targetRepository
                            .findAll()
                            .stream()
                            .filter(t ->
                                    t.getAnnouncement()
                                            .getId()
                                            .equals(announcement.getId()))
                            .findFirst()
                            .orElse(null);

                    return AnnouncementMapper.toDto(announcement, target);
                })
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Announcement announcement = findAnnouncement(id);
        announcement.setActive(false);

        announcementRepository.save(announcement);
    }


    private Announcement findAnnouncement(Long id) {

        return announcementRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.ANNOUNCEMENT_NOT_FOUND));
    }

    private void validateTarget(
            AnnouncementRequest request) {

        if (request.targetType() == null) {
            throw new ValidationException(ErrorCode.INVALID_ANNOUNCEMENT_TARGET);
        }

        switch (request.targetType()) {

            case ALL -> {
                if (request.targetRole() != null || request.targetId() != null) {
                    throw new ValidationException(ErrorCode.INVALID_ANNOUNCEMENT_TARGET);
                }
            }

            case ROLE -> {
                if (request.targetRole() == null || request.targetId() != null) {

                    throw new ValidationException(ErrorCode.INVALID_ANNOUNCEMENT_TARGET);
                }
            }

            case EDUCATION_STAGE,
                 GRADE_LEVEL,
                 SCHOOL_CLASS,
                 STUDENT,
                 USER -> {

                if (request.targetId() == null || request.targetRole() != null) {

                    throw new ValidationException(ErrorCode.INVALID_ANNOUNCEMENT_TARGET);
                }
            }
        }
    }
}