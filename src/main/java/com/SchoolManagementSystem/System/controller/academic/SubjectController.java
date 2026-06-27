package com.SchoolManagementSystem.System.controller.academic;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.academic.SubjectDto;
import com.SchoolManagementSystem.System.service.CrudService;
import com.SchoolManagementSystem.System.service.academic.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    SubjectService subjectService;

    @PreAuthorize("hasRole('PRINCIPAL')")
    public ResponseEntity<SubjectDto> create(SubjectDto dto) {
        return ResponseEntity.ok(subjectService.save(dto));
    }
    @PreAuthorize("hasRole('PRINCIPAL')")
    public ResponseEntity<SubjectDto> update(Long id, SubjectDto dto) {
        return ResponseEntity.ok(subjectService.update(id, dto));
    }

    public ResponseEntity<SubjectDto> getById(Long id) {
        return ResponseEntity.ok(subjectService.getById(id));
    }

    public ResponseEntity<List<SubjectDto>> getAll() {
        return ResponseEntity.ok(subjectService.getAll());
    }

    public void delete(Long id) {
        subjectService.delete(id);
    }
}