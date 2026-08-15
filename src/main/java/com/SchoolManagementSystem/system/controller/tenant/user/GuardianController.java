package com.SchoolManagementSystem.System.controller.tenant.user;

import com.SchoolManagementSystem.System.dto.user.GuardianDto;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.user.GuardianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guardians")
@RequiredArgsConstructor
public class GuardianController {

    private final GuardianService guardianService;

    @PutMapping("/{id}")
    public ResponseEntity<GuardianDto> update(@PathVariable Long id,@RequestBody GuardianDto dto) {
        return ResponseEntity.ok(guardianService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuardianDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(guardianService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<GuardianDto>> getAll() {
        return ResponseEntity.ok(guardianService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        guardianService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<GuardianDto> me(@AuthenticationPrincipal UserPrincipal userPrincipal) {

        return ResponseEntity.ok(guardianService.getById(userPrincipal.getRefId()));
    }
}