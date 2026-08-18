package com.SchoolManagementSystem.system.controller.tenant.communication;

import com.SchoolManagementSystem.system.dto.communication.AnnouncementDto;
import com.SchoolManagementSystem.system.dto.communication.NotificationTopicsDto;
import com.SchoolManagementSystem.system.dto.communication.request.AnnouncementRequest;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.communication.AnnouncementService;
import com.SchoolManagementSystem.system.service.communication.NotificationTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final NotificationTopicService notificationTopicService;


    @PostMapping
    public ResponseEntity<AnnouncementDto> create(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody AnnouncementRequest request) {

        return ResponseEntity.ok(announcementService.create(userPrincipal, request));
    }


    @GetMapping
    public ResponseEntity<List<AnnouncementDto>> getAll() {

        return ResponseEntity.ok(announcementService.getAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDto> getById(@PathVariable Long id) {

        return ResponseEntity.ok(announcementService.getById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        announcementService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/me-topics")
    public ResponseEntity<NotificationTopicsDto> getTopics(@AuthenticationPrincipal UserPrincipal userPrincipal) {

        return ResponseEntity.ok(notificationTopicService.getTopics(userPrincipal));
    }
}