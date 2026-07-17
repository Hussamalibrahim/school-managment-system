package com.SchoolManagementSystem.System.controller.student;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.student.StudentDto;
import com.SchoolManagementSystem.System.dto.user.TeacherDto;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.security.dto.AuthRequestStudent;
import com.SchoolManagementSystem.System.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @PutMapping("/{studentId}/assign-class/{classId}")
    public ResponseEntity<StudentDto> assignClass(
            @PathVariable Long studentId,
            @PathVariable Long classId)
    {
        return ResponseEntity.ok(studentService.assignClass(studentId, classId));
    }


    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable Long id,@RequestBody StudentDto dto) {
        return ResponseEntity.ok(studentService.update(id, dto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<StudentDto> me(Authentication auth) {


        UserPrincipal user = (UserPrincipal) auth.getPrincipal();

        log.info(user.getRole().toString());
        log.info(user.getAuthorities().toString());

        return ResponseEntity.ok(studentService.getById(user.getRefId()));
    }
}