package com.SchoolManagementSystem.System.service.communication.impl;

import com.SchoolManagementSystem.System.dto.communication.AnnouncementDto;
import com.SchoolManagementSystem.System.mapper.communication.AnnouncementMapper;
import com.SchoolManagementSystem.System.entity.communication.Announcement;
import com.SchoolManagementSystem.System.repository.communication.AnnouncementRepository;
import com.SchoolManagementSystem.System.service.communication.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository repository;

    @Override
    public AnnouncementDto save(AnnouncementDto dto) {
        Announcement announcement = AnnouncementMapper.toEntity(dto);
        announcement = repository.save(announcement);
        return AnnouncementMapper.toDto(announcement);
    }

    @Override
    public AnnouncementDto update(Long id, AnnouncementDto dto) {
        Announcement announcement = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));

        announcement.setTitle(dto.title());
        announcement.setContent(dto.content());
        announcement.setUserType(dto.userType());
        announcement.setStatus(dto.status());
        announcement.setTargetId(dto.targetId());
        announcement.setPublishDate(dto.publishDate());
        announcement.setStatus(dto.status());

        announcement = repository.save(announcement);
        return AnnouncementMapper.toDto(announcement);
    }

    @Override
    public AnnouncementDto getById(Long id) {
        return repository.findById(id)
                .map(AnnouncementMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));
    }

    @Override
    public List<AnnouncementDto> getAll() {
        return repository.findAll()
                .stream()
                .map(AnnouncementMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
