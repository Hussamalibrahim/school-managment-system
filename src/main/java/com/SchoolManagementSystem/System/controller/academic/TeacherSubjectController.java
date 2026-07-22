package com.SchoolManagementSystem.System.controller.academic;

import com.SchoolManagementSystem.System.dto.academic.SubjectDto;
import com.SchoolManagementSystem.System.dto.academic.TeacherSubjectDto;
import com.SchoolManagementSystem.System.dto.user.TeacherDto;
import com.SchoolManagementSystem.System.service.academic.TeacherSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teacher-subjects")
public class TeacherSubjectController {

    private final TeacherSubjectService teacherSubjectService;


    @PostMapping("/assign/{teacherId}/{subjectId}")
    public ResponseEntity<TeacherSubjectDto> connect(
            @PathVariable Long teacherId,
            @PathVariable Long subjectId) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(teacherSubjectService.connectTeacherToSubject(teacherId, subjectId));
    }
    @GetMapping("/{teacherId}")
    public ResponseEntity<List<SubjectDto>> getSubjectByTeacher(
            @PathVariable Long teacherId) {

        return ResponseEntity
                .ok(teacherSubjectService.getSubjectByTeacherId(teacherId));
    }
    @GetMapping("/teach-subject/{subjectId}")
    public ResponseEntity<List<TeacherDto>> getSubjectTeacher(
            @PathVariable Long subjectId) {

        return ResponseEntity
                .ok(teacherSubjectService.getTeacherBySubjectId(subjectId));
    }
}