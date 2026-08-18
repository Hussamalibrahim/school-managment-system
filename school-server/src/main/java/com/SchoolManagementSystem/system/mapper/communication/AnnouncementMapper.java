package com.SchoolManagementSystem.system.mapper.communication;

import com.SchoolManagementSystem.system.dto.communication.AnnouncementDto;
import com.SchoolManagementSystem.system.dto.communication.request.AnnouncementRequest;
import com.SchoolManagementSystem.system.entity.communication.Announcement;
import com.SchoolManagementSystem.system.entity.communication.AnnouncementTarget;


public class AnnouncementMapper {

    private AnnouncementMapper() {
    }

    public static AnnouncementDto toDto(Announcement announcement, AnnouncementTarget target) {

        return new AnnouncementDto(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                target.getType(),
                target.getTargetRole(),
                target.getTargetId(),
                announcement.getCreatedAt(),
                announcement.getActive()
        );
    }

        public static void requestToEntity(Announcement announcement, AnnouncementRequest request) {
            announcement.setTitle(request.title());
            announcement.setContent(request.content());
    }
}