package com.SchoolManagementSystem.System.controller.academic;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.academic.TeacherSubjectDto;
import com.SchoolManagementSystem.System.service.academic.TeacherSubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher-subjects")
public class TeacherSubjectController
        extends BaseCrudController<TeacherSubjectDto> {

    private final TeacherSubjectService teacherSubjectService;

    public TeacherSubjectController(
            TeacherSubjectService service) {
        super(service);
        this.teacherSubjectService = service;
    }

    @PostMapping("/connect/{teacherId}/{subjectId}")
    public ResponseEntity<TeacherSubjectDto> connect(
            @PathVariable Long teacherId,
            @PathVariable Long subjectId) {

        return ResponseEntity.ok(
                teacherSubjectService.connectTeacherToSubject(
                        teacherId,
                        subjectId
                )
        );
    }
}