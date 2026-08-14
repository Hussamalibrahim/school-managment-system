package com.SchoolManagementSystem.System.controller.tenant.user;

import com.SchoolManagementSystem.System.dto.academic.ClassScheduleDto;
import com.SchoolManagementSystem.System.dto.academic.SchoolClassDto;
import com.SchoolManagementSystem.System.dto.request.CreateUserRequest;
import com.SchoolManagementSystem.System.dto.user.PrincipalDto;
import com.SchoolManagementSystem.System.entity.enumeration.FileOwnerType;
import com.SchoolManagementSystem.System.entity.enumeration.UserType;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.academic.ClassScheduleService;
import com.SchoolManagementSystem.System.service.academic.SchoolClassService;
import com.SchoolManagementSystem.System.service.user.PrincipalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/principal")
@RequiredArgsConstructor
public class PrincipalController {

    private final SchoolClassService schoolClassService;
    private final ClassScheduleService classScheduleService;
    private final PrincipalService principalService;

    // ADD Secretary, Librarian, Teacher Only
    @PostMapping("/create-user")
    public ResponseEntity<Void> createStaff(@RequestBody CreateUserRequest request) {
        principalService.createStaff(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/assign-schedule")
    public ResponseEntity<ClassScheduleDto> assignSchedule(
            @RequestParam Long scheduleId,
            @RequestParam Long teacherId,
            @RequestParam Long subjectId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(classScheduleService.assignTeacher(scheduleId, teacherId, subjectId));
    }

    @PostMapping("/create-class")
    public ResponseEntity<Void> createClass(@RequestBody SchoolClassDto request) {
        schoolClassService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<PrincipalDto> me(@AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(principalService.getById(user.getRefId()));
    }
}