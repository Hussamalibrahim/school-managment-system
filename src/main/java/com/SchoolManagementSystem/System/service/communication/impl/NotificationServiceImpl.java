package com.SchoolManagementSystem.System.service.communication.impl;

import com.SchoolManagementSystem.System.dto.communication.NotificationDto;
import com.SchoolManagementSystem.System.dtoMapper.communication.NotificationMapper;
import com.SchoolManagementSystem.System.entity.communication.Notification;
import com.SchoolManagementSystem.System.repository.communication.NotificationRepository;
import com.SchoolManagementSystem.System.service.communication.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Override
    public NotificationDto save(NotificationDto dto) {
        Notification notification = NotificationMapper.toEntity(dto);
        notification = repository.save(notification);
        return NotificationMapper.toDto(notification);
    }

    @Override
    public NotificationDto update(Long id, NotificationDto dto) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setTitle(dto.title());
        notification.setMessage(dto.message());
        notification.setUserType(dto.userType());
        notification.setTargetId(dto.targetId());
        notification.setIsRead(dto.isRead());
        notification.setNotificationDate(dto.notificationDate());

        notification = repository.save(notification);
        return NotificationMapper.toDto(notification);
    }

    @Override
    public NotificationDto getById(Long id) {
        return repository.findById(id)
                .map(NotificationMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    @Override
    public List<NotificationDto> getAll() {
        return repository.findAll()
                .stream()
                .map(NotificationMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
