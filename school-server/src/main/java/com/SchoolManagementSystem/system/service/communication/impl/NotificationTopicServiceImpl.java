package com.SchoolManagementSystem.system.service.communication.impl;

import com.SchoolManagementSystem.system.dto.communication.NotificationTopicsDto;
import com.SchoolManagementSystem.system.dto.communication.respones.NotificationTopicDto;
import com.SchoolManagementSystem.system.entity.communication.Announcement;
import com.SchoolManagementSystem.system.entity.communication.AnnouncementTarget;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import com.SchoolManagementSystem.system.entity.school.School;
import com.SchoolManagementSystem.system.entity.student.Student;
import com.SchoolManagementSystem.system.entity.student.StudentGuardian;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.repository.communication.AnnouncementRepository;
import com.SchoolManagementSystem.system.repository.communication.AnnouncementTargetRepository;
import com.SchoolManagementSystem.system.repository.school.SchoolRepository;
import com.SchoolManagementSystem.system.repository.student.StudentGuardianRepository;
import com.SchoolManagementSystem.system.repository.student.StudentRepository;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.communication.NotificationTopicService;
import com.SchoolManagementSystem.system.tenant.TenantContext;
import com.SchoolManagementSystem.system.utils.NotificationTopicUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationTopicServiceImpl implements NotificationTopicService {

    private final SchoolRepository schoolRepository;
    private final StudentGuardianRepository studentGuardianRepository;

    private final AnnouncementRepository announcementRepository;
    private final StudentRepository studentRepository;
    private final AnnouncementTargetRepository announcementTargetRepository;

    @Override
    @Transactional(readOnly = true)
    public NotificationTopicsDto getTopics(
            UserPrincipal userPrincipal
    ) {

        Long schoolId = TenantContext.getSchoolId();

        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() ->
                        new NotFoundException(
                                ErrorCode.SCHOOL_NOT_FOUND
                        ));

        String schoolCode = school.getCode();

        List<String> topicNames =
                buildUserTopics(
                        userPrincipal,
                        schoolCode
                );

        Role role = userPrincipal.getRole();

        if (role == Role.STUDENT) {

            Student student =
                    studentRepository.findById(
                                    userPrincipal.getRefId()
                            )
                            .orElseThrow(() ->
                                    new NotFoundException(
                                            ErrorCode.STUDENT_NOT_FOUND
                                    ));

            addStudentTopics(
                    topicNames,
                    student,
                    schoolCode
            );
        }

        if (role == Role.GUARDIAN) {

            addGuardianTopics(
                    topicNames,
                    userPrincipal.getRefId(),
                    schoolCode
            );
        }

        List<Announcement> announcements =
                announcementRepository
                        .findBySchoolIdAndActiveTrueOrderByCreatedAtDesc(
                                schoolId
                        );

        List<NotificationTopicDto> result =
                new ArrayList<>();

        for (Announcement announcement : announcements) {

            AnnouncementTarget target =
                    announcementTargetRepository
                            .findByAnnouncementId(
                                    announcement.getId()
                            )
                            .orElse(null);

            String topic =
                    buildTopic(
                            schoolCode,
                            target
                    );

            if (topic != null &&
                    topicNames.contains(topic)) {

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

        if (target == null ||
                target.getType() == null) {
            return null;
        }

        return switch (target.getType()) {

            case ALL ->
                    NotificationTopicUtil.all(
                            schoolCode
                    );

            case ROLE ->
                    target.getTargetRole() == null
                            ? null
                            : NotificationTopicUtil.role(
                            schoolCode,
                            target.getTargetRole()
                    );


            case GRADE_LEVEL ->
                    target.getTargetGradeLevel() == null
                            ? null
                            : NotificationTopicUtil.gradeLevel(
                            schoolCode,
                            target.getTargetGradeLevel()
                    );

            case SCHOOL_CLASS ->
                    target.getTargetId() == null
                            ? null
                            : NotificationTopicUtil.schoolClass(
                            schoolCode,
                            target.getTargetId()
                    );

            case STUDENT ->
                    target.getTargetId() == null
                            ? null
                            : NotificationTopicUtil.student(
                            schoolCode,
                            target.getTargetId()
                    );

            case USER ->
                    target.getTargetId() == null
                            ? null
                            : NotificationTopicUtil.user(
                            schoolCode,
                            target.getTargetId()
                    );
        };
    }
    private List<String> buildUserTopics(
            UserPrincipal user,
            String schoolCode
    ) {
        List<String> topics = new ArrayList<>();

        // الجميع
        topics.add(NotificationTopicUtil.all(schoolCode));

        // Role
        topics.add(
                NotificationTopicUtil.role(
                        schoolCode,
                        user.getRole()
                )
        );

        // USER
        topics.add(
                NotificationTopicUtil.user(
                        schoolCode,
                        user.getRefId()
                )
        );

        return topics;
    }
    private void addStudentTopics(
            List<String> topics,
            Student student,
            String schoolCode
    ) {
        // الطالب نفسه
        topics.add(
                NotificationTopicUtil.student(
                        schoolCode,
                        student.getId()
                )
        );

        // الصف
        if (student.getStudentSchoolClass() != null) {
            topics.add(
                    NotificationTopicUtil.schoolClass(
                            schoolCode,
                            student.getStudentSchoolClass().getId()
                    )
            );
        }

        // Grade Level
        if (student.getGradeLevel() != null) {
            topics.add(
                    NotificationTopicUtil.gradeLevel(
                            schoolCode,
                            student.getGradeLevel()
                    )
            );
        }
    }
    private void addGuardianTopics(
            List<String> topics,
            Long guardianId,
            String schoolCode
    ) {

        List<StudentGuardian> relations =
                studentGuardianRepository.findByGuardianId(guardianId);

        for (StudentGuardian relation : relations) {

            Student student = relation.getStudent();

            addStudentTopics(
                    topics,
                    student,
                    schoolCode
            );
        }
    }
}