package com.SchoolManagementSystem.System.mapper.communication;

import com.SchoolManagementSystem.System.dto.communication.AnnouncementDto;
import com.SchoolManagementSystem.System.entity.communication.Announcement;

public final class AnnouncementMapper
{
    private AnnouncementMapper()
    {
    }

    public static AnnouncementDto toDto(Announcement announcement)
    {
        if (announcement == null)
            return null;

        return new AnnouncementDto(
                announcement.getId(),
                announcement.getCreatedAt(),
                announcement.getUpdatedAt(),
                announcement.getDeletedAt(),

                announcement.getTitle(),
                announcement.getContent(),

                announcement.getUserType(),

                announcement.getTargetId(),

                announcement.getPublishDate(),

                announcement.getStatus()
        );
    }

    public static Announcement toEntity(AnnouncementDto dto)
    {
        if (dto == null)
            return null;

        Announcement announcement = new Announcement();

        announcement.setTitle(dto.title());
        announcement.setContent(dto.content());

        announcement.setUserType(dto.userType());

        announcement.setTargetId(dto.targetId());

        announcement.setPublishDate(dto.publishDate());

        announcement.setStatus(dto.status());

        return announcement;
    }
}