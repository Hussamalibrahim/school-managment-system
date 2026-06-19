package com.SchoolManagementSystem.System.mapper.communication;


import com.SchoolManagementSystem.System.dto.communication.NotificationDto;
import com.SchoolManagementSystem.System.entity.communication.Notification;

public final class NotificationMapper {
     private NotificationMapper(){}
     public static NotificationDto toDto(Notification notification) {
          if (notification == null) return null;

          return new NotificationDto(
                  notification.getId(),
                  notification.getCreatedAt(),
                  notification.getUpdatedAt(),
                  notification.getDeletedAt(),
                  notification.getTitle(),
                  notification.getMessage(),
                  notification.getUserType(),
                  notification.getTargetId(),
                  notification.getIsRead(),
                  notification.getNotificationDate()
          );
     }

     public static Notification toEntity(NotificationDto dto) {
          if (dto == null) return null;

          Notification notification = new Notification();
          notification.setId(dto.id());
          notification.setCreatedAt(dto.createdAt());
          notification.setUpdatedAt(dto.updatedAt());
          notification.setDeletedAt(dto.deletedAt());
          notification.setTitle(dto.title());
          notification.setMessage(dto.message());
          notification.setUserType(dto.userType());
          notification.setTargetId(dto.targetId());
          notification.setIsRead(dto.isRead());
          notification.setNotificationDate(dto.notificationDate());

          return notification;
     }
}