package com.SchoolManagementSystem.System.controller.academic;

import com.SchoolManagementSystem.System.dto.academic.ClassScheduleDto;
import com.SchoolManagementSystem.System.service.academic.ClassScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ClassScheduleController {

    private final ClassScheduleService classScheduleService;

    @PostMapping("/extra/{classId}")
    @PreAuthorize("hasRole('PRINCIPAL')")
    public ClassScheduleDto addExtra(@PathVariable Long classId) {
        return classScheduleService.addExtraPeriod(classId);
    }

    @GetMapping("/{classId}")
    public List<ClassScheduleDto> get(@PathVariable Long classId) {
        return classScheduleService.getByClass(classId);
    }

    @GetMapping
    public ResponseEntity<List<ClassScheduleDto>> getAll()
    {
        return ResponseEntity.ok(classScheduleService.getAll());
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<ClassScheduleDto>> getByTeacher(@PathVariable Long teacherId)
    {
        return ResponseEntity.ok(classScheduleService.getByTeacher(teacherId));

    }
    @PreAuthorize("hasRole('PRINCIPAL')")
    @PostMapping("/class/{classId}/extra-period")
    public ResponseEntity<ClassScheduleDto> addExtraPeriod(@PathVariable Long classId)
    {
        return ResponseEntity.ok(classScheduleService.addExtraPeriod(classId));
    }
    @PutMapping("/{scheduleId}/assign")
    public ResponseEntity<ClassScheduleDto> assignTeacher(
            @PathVariable Long scheduleId,
            @RequestParam Long teacherId,
            @RequestParam Long subjectId)
    {
        return ResponseEntity.ok(classScheduleService.assignTeacher(scheduleId, teacherId, subjectId));
    }
}