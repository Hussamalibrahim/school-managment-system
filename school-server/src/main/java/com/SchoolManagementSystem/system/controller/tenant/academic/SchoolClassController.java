package com.SchoolManagementSystem.system.controller.tenant.academic;

import com.SchoolManagementSystem.system.dto.academic.SchoolClassDto;
import com.SchoolManagementSystem.system.dto.student.StudentDto;
import com.SchoolManagementSystem.system.service.academic.SchoolClassService;
import com.SchoolManagementSystem.system.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/classes")
public class SchoolClassController
{
    private final SchoolClassService schoolClassService;
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<SchoolClassDto> create(
            @RequestBody SchoolClassDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(schoolClassService.save(dto));
    }
    @GetMapping
    public ResponseEntity<List<SchoolClassDto>> getAll()
    {
        return ResponseEntity.ok(schoolClassService.getAll());
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<List<StudentDto>> getStudentsByClass_Id(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentsByClass_Id(id));
    }

}