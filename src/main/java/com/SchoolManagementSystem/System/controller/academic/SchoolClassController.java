package com.SchoolManagementSystem.System.controller.academic;

import com.SchoolManagementSystem.System.dto.academic.SchoolClassDto;
import com.SchoolManagementSystem.System.service.academic.SchoolClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/classes")
public class SchoolClassController
{
    private final SchoolClassService schoolClassService;

    @PreAuthorize("hasRole('PRINCIPAL')")
    @PostMapping
    public ResponseEntity<SchoolClassDto> create(@RequestBody SchoolClassDto dto)
    {
        return ResponseEntity.ok(schoolClassService.save(dto));
    }
    @GetMapping
    public ResponseEntity<List<SchoolClassDto>> getAll()
    {
        return ResponseEntity.ok(schoolClassService.getAll());
    }
}