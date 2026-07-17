package com.SchoolManagementSystem.System.controller.academic;

import com.SchoolManagementSystem.System.controller.BaseCrudController;
import com.SchoolManagementSystem.System.dto.academic.TeacherSubjectDto;
import com.SchoolManagementSystem.System.service.academic.TeacherSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teacher-subjects")
public class TeacherSubjectController {

    private final TeacherSubjectService teacherSubjectService;


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