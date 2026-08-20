package com.SchoolManagementSystem.system.service.communication.impl;

import com.SchoolManagementSystem.system.dto.communication.NotificationTopicsDto;
import com.SchoolManagementSystem.system.dto.communication.respones.NotificationTopicDto;
import com.SchoolManagementSystem.system.entity.communication.Announcement;
import com.SchoolManagementSystem.system.entity.communication.AnnouncementTarget;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import com.SchoolManagementSystem.system.entity.school.School;
import com.SchoolManagementSystem.system.entity.student.StudentGuardian;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.repository.communication.AnnouncementRepository;
import com.SchoolManagementSystem.system.repository.communication.AnnouncementTargetRepository;
import com.SchoolManagementSystem.system.repository.school.SchoolRepository;
import com.SchoolManagementSystem.system.repository.student.StudentGuardianRepository;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.communication.NotificationTopicService;
import com.SchoolManagementSystem.system.tenant.TenantContext;
import com.SchoolManagementSystem.system.utils.NotificationTopicUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationTopicServiceImpl implements NotificationTopicService {

    private final SchoolRepository schoolRepository;
    private final StudentGuardianRepository studentGuardianRepository;

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementTargetRepository announcementTargetRepository;

    @Override
    public NotificationTopicsDto getTopics(UserPrincipal userPrincipal) {

        Long schoolId = TenantContext.getSchoolId();

        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.SCHOOL_NOT_FOUND));

        String schoolCode = school.getCode();

        Role role = userPrincipal.getRole();

        List<String> topicNames = new ArrayList<>();

        // جميع المستخدمين
        topicNames.add(
                NotificationTopicUtil.all(schoolCode)
        );

        // Role المستخدم
        topicNames.add(
                NotificationTopicUtil.role(schoolCode, role)
        );

        // ولي الأمر + أولاده
        if (role == Role.GUARDIAN) {

            List<StudentGuardian> relations =
                    studentGuardianRepository
                            .findByGuardianId(userPrincipal.getRefId());

            relations.forEach(relation ->
                    topicNames.add(
                            NotificationTopicUtil.student(
                                    schoolCode,
                                    relation.getStudent().getId()
                            )
                    )
            );
        }

        List<Announcement> announcements =
                announcementRepository
                        .findBySchoolIdAndActiveTrueOrderByCreatedAtDesc(
                                schoolId
                        );

        List<NotificationTopicDto> result = new ArrayList<>();

        for (Announcement announcement : announcements) {

            AnnouncementTarget target =
                    announcementTargetRepository
                            .findByAnnouncementId(announcement.getId())
                            .orElse(null);

            String topic =
                    buildTopic(schoolCode, target);

            if (topic != null && topicNames.contains(topic)) {

                result.add(
                        new NotificationTopicDto(
                                topic,
                                announcement.getId(),
                                announcement.getTitle(),
                                announcement.getContent(),
                                announcement.getCreatedAt()
                        )
                );
            }
        }

        return new NotificationTopicsDto(result);
    }

    private String buildTopic(
            String schoolCode,
            AnnouncementTarget target
    ) {

        if (target == null || target.getType() == null) {
            return null;
        }

        return switch (target.getType()) {

            case ALL ->
                    NotificationTopicUtil.all(schoolCode);

            case ROLE ->
                    NotificationTopicUtil.role(
                            schoolCode,
                            target.getTargetRole()
                    );

            case STUDENT ->
                    NotificationTopicUtil.student(
                            schoolCode,
                            target.getTargetId()
                    );
            default -> throw new IllegalStateException("Unexpected value: " + target.getType());
        };
    }
}