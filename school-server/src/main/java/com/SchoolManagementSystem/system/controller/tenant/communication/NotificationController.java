package com.SchoolManagementSystem.system.controller.tenant.communication;

import com.SchoolManagementSystem.system.dto.communication.NotificationDto;
import com.SchoolManagementSystem.system.service.communication.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    public NotificationDto create(@RequestBody NotificationDto dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public NotificationDto update(@PathVariable Long id, @RequestBody NotificationDto dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public NotificationDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<NotificationDto> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}