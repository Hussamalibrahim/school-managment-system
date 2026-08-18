package com.SchoolManagementSystem.system.controller.tenant.finance;

import com.SchoolManagementSystem.system.dto.finance.request.DiscountRequest;
import com.SchoolManagementSystem.system.dto.finance.DiscountDto;
import com.SchoolManagementSystem.system.service.finance.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fees")
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping("/{feeId}/discount")
    public ResponseEntity<DiscountDto> apply(@PathVariable Long feeId, @RequestBody DiscountRequest request) {

        return ResponseEntity.ok(discountService.apply(feeId, request));
    }

    @GetMapping("/{feeId}/discount")
    public ResponseEntity<DiscountDto> getByFee(@PathVariable Long feeId) {

        return ResponseEntity.ok(discountService.getByFee(feeId));
    }
    @GetMapping("/discount")
    public ResponseEntity<List<DiscountDto>> getAll() {

        return ResponseEntity.ok(discountService.getAll());
    }

    @DeleteMapping("/{feeId}/discount")
    public ResponseEntity<Void> delete(@PathVariable Long feeId) {

        discountService.delete(feeId);
        return ResponseEntity.noContent().build();
    }
}