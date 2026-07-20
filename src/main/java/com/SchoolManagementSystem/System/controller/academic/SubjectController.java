package com.SchoolManagementSystem.System.controller.academic;

import com.SchoolManagementSystem.System.dto.academic.SubjectDto;
import com.SchoolManagementSystem.System.dto.academic.request.SubjectCreateRequest;
import com.SchoolManagementSystem.System.dto.academic.request.SubjectNameDto;
import com.SchoolManagementSystem.System.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.System.entity.enumeration.Semester;
import com.SchoolManagementSystem.System.service.academic.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectDto> create(@RequestBody SubjectCreateRequest dto) {
        return ResponseEntity.ok(subjectService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDto> update(@PathVariable Long id, @RequestBody SubjectDto dto) {
        return ResponseEntity.ok(subjectService.update(id, dto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<SubjectDto>> getAll() {
        return ResponseEntity.ok(subjectService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<SubjectDto>> search(
            @RequestParam(required = false) GradeLevel gradeLevel,
            @RequestParam(required = false) Semester semester) {
        if (semester == null) {
            return ResponseEntity.ok(
                    subjectService.getByGrade(gradeLevel)
            );
        }
        if (gradeLevel == null) {
            return ResponseEntity.ok(
                    subjectService.getBySemester(semester)
            );
        }

        return ResponseEntity.ok(
                subjectService.getSubjectByGradeAndSemester(gradeLevel, semester)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        subjectService.delete(id);
    }
}