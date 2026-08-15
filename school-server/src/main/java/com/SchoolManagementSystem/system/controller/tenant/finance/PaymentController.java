package com.SchoolManagementSystem.system.controller.tenant.finance;

import com.SchoolManagementSystem.system.dto.finance.PaymentDto;
import com.SchoolManagementSystem.system.service.finance.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public PaymentDto create(@RequestBody PaymentDto dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public PaymentDto update(@PathVariable Long id, @RequestBody PaymentDto dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public PaymentDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<PaymentDto> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}