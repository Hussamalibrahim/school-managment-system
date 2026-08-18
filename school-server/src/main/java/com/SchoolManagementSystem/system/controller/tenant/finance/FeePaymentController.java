package com.SchoolManagementSystem.system.controller.tenant.finance;

import com.SchoolManagementSystem.system.dto.finance.request.FeePaymentRequest;
import com.SchoolManagementSystem.system.dto.finance.response.FeePaymentDto;
import com.SchoolManagementSystem.system.dto.finance.response.GuardianFeesResponse;
import com.SchoolManagementSystem.system.security.UserPrincipal;
import com.SchoolManagementSystem.system.service.finance.FeePaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fees")
public class FeePaymentController {

    private final FeePaymentService feePaymentService;

    @PostMapping("/{feeId}/payments")
    public ResponseEntity<FeePaymentDto> save(@PathVariable Long feeId, @RequestBody FeePaymentRequest request) {

        return ResponseEntity.ok(feePaymentService.save(feeId, request));
    }

    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<FeePaymentDto> getById(@PathVariable Long paymentId) {

        return ResponseEntity.ok(feePaymentService.getById(paymentId));
    }
    @GetMapping("/payments/receipt/{receiptNumber}")
    public ResponseEntity<FeePaymentDto> getByReceiptNumber(@PathVariable String receiptNumber) {

        return ResponseEntity.ok(feePaymentService.getByReceiptNumber(receiptNumber));
    }

    @GetMapping("/{feeId}/payments")
    public ResponseEntity<List<FeePaymentDto>> getByFee(@PathVariable Long feeId) {

        return ResponseEntity.ok(feePaymentService.getByFee(feeId));
    }

    @GetMapping("/student/{studentId}/payments")
    public ResponseEntity<List<FeePaymentDto>> getByStudent(@PathVariable Long studentId) {

        return ResponseEntity.ok(feePaymentService.getByStudent(studentId));
    }

    @DeleteMapping("/payments/{paymentId}")
    public ResponseEntity<Void> delete(@PathVariable Long paymentId) {

        feePaymentService.delete(paymentId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/guardian/me")
    public ResponseEntity<GuardianFeesResponse> getMyFees(@AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(feePaymentService.getGuardianFees(user.getRefId()));
    }
}