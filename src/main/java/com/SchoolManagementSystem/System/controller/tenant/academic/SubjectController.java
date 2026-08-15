package com.SchoolManagementSystem.System.controller.tenant.academic;

import com.SchoolManagementSystem.System.dto.academic.SubjectDto;
import com.SchoolManagementSystem.System.dto.academic.request.SubjectCreateRequest;
import com.SchoolManagementSystem.System.entity.enumeration.GradeLevel;
import com.SchoolManagementSystem.System.entity.enumeration.SemesterName;
import com.SchoolManagementSystem.System.service.academic.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectDto> create(@RequestBody SubjectCreateRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subjectService.save(dto));
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
            @RequestParam(required = false) SemesterName semesterName) {
        if (semesterName == null) {
            return ResponseEntity.ok(subjectService.getByGrade(gradeLevel));
        }
        if (gradeLevel == null) {
            return ResponseEntity.ok(subjectService.getBySemester(semesterName));
        }
        return ResponseEntity.ok(subjectService.getSubjectByGradeAndSemester(gradeLevel, semesterName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        subjectService.delete(id);

        return ResponseEntity.noContent().build();
    }
}