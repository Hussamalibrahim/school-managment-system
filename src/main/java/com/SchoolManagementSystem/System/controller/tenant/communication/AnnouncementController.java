package com.SchoolManagementSystem.System.controller.communication;

import com.SchoolManagementSystem.System.dto.communication.AnnouncementDto;
import com.SchoolManagementSystem.System.service.communication.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService service;

    @PostMapping
    public AnnouncementDto create(@RequestBody AnnouncementDto dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public AnnouncementDto update(@PathVariable Long id, @RequestBody AnnouncementDto dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public AnnouncementDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<AnnouncementDto> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}