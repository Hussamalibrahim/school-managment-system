package com.SchoolManagementSystem.System.controller.tenant.finance;

import com.SchoolManagementSystem.System.dto.finance.PaymentDto;
import com.SchoolManagementSystem.System.exception.business.ValidationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import com.SchoolManagementSystem.System.service.finance.PaymentService;
import com.SchoolManagementSystem.System.service.student.StudentGuardianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;
    private final StudentGuardianService studentGuardianService;

    @PostMapping
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<PaymentDto> create(@RequestBody PaymentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<PaymentDto> update(@PathVariable Long id, @RequestBody PaymentDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<PaymentDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<List<PaymentDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<PaymentDto>> getMyPayments(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(service.getPaymentsByStudentId(user.getRefId()));
    }

    @GetMapping("/guardian/student/{studentId}")
    @PreAuthorize("hasRole('GUARDIAN')")
    public ResponseEntity<List<PaymentDto>> getStudentPaymentsForGuardian(
            @PathVariable Long studentId,
            @AuthenticationPrincipal UserPrincipal user) {
        if (!studentGuardianService.isStudentBelongsToGuardian(studentId, user.getRefId())) {
            throw new ValidationException(ErrorCode.UNAUTHENTICATED);
        }
        return ResponseEntity.ok(service.getPaymentsByStudentId(studentId));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('PRINCIPAL','SECRETARY')")
    public ResponseEntity<List<PaymentDto>> getStudentPayments(@PathVariable Long studentId) {
        return ResponseEntity.ok(service.getPaymentsByStudentId(studentId));
    }
}