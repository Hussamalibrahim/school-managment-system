package com.SchoolManagementSystem.System.controller.file;


import com.SchoolManagementSystem.System.dto.file.FileDto;
import com.SchoolManagementSystem.System.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService service;

    @PostMapping
    public FileDto create(@RequestBody FileDto dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public FileDto update(@PathVariable Long id, @RequestBody FileDto dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public FileDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<FileDto> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
