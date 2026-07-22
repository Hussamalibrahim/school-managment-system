package com.SchoolManagementSystem.System.controller.academic;

import com.SchoolManagementSystem.System.dto.academic.ClassScheduleDto;
import com.SchoolManagementSystem.System.service.academic.ClassScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ClassScheduleController {

    private final ClassScheduleService classScheduleService;

    @GetMapping("/{classId}")
    public ResponseEntity<List<ClassScheduleDto>> get(@PathVariable Long classId) {
        return ResponseEntity.ok(classScheduleService.getByClass(classId));
    }

    @GetMapping
    public ResponseEntity<List<ClassScheduleDto>> getAll() {
        return ResponseEntity.ok(classScheduleService.getAll());
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<ClassScheduleDto>> getByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(classScheduleService.getByTeacher(teacherId));
    }

    @PostMapping("/class/{classId}/extra-period/{day}")
    public ResponseEntity<List<ClassScheduleDto>> addExtraPeriod(@PathVariable Long classId, @PathVariable DayOfWeek day) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(classScheduleService.addExtraPeriod(classId, day));
    }

    @PutMapping("/{scheduleId}/assign")
    public ResponseEntity<ClassScheduleDto> assignTeacher(
            @PathVariable Long scheduleId,
            @RequestParam Long teacherId,
            @RequestParam Long subjectId) {
        return ResponseEntity.ok(classScheduleService.assignTeacher(scheduleId, teacherId, subjectId));
    }
}