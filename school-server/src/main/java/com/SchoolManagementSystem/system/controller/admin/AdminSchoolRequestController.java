package com.SchoolManagementSystem.system.controller.admin;

import com.SchoolManagementSystem.system.dto.school.SchoolRequestDto;
import com.SchoolManagementSystem.system.dto.school.request.RejectSchoolRequest;
import com.SchoolManagementSystem.system.service.school.SchoolRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/school-requests")
@RequiredArgsConstructor
public class AdminSchoolRequestController {

    private final SchoolRequestService schoolRequestService;


    @GetMapping
    public ResponseEntity<List<SchoolRequestDto>> getAll() {

        return ResponseEntity.ok(schoolRequestService.getAll());
    }


    @GetMapping("/pending")
    public ResponseEntity<List<SchoolRequestDto>> getPending() {

        return ResponseEntity.ok(schoolRequestService.getPending());
    }


    @GetMapping("/{id}")
    public ResponseEntity<SchoolRequestDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(schoolRequestService.getById(id));
    }


    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approve(
            @PathVariable Long id) {

        schoolRequestService.approve(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> reject(
            @PathVariable Long id,
            @RequestBody RejectSchoolRequest request) {

        schoolRequestService.reject(id, request.reason());

        return ResponseEntity.noContent().build();
    }
}