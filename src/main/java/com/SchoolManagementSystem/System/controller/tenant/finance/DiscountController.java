package com.SchoolManagementSystem.System.controller.finance;

import com.SchoolManagementSystem.System.dto.finance.DiscountDto;
import com.SchoolManagementSystem.System.service.finance.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService service;

    @PostMapping
    public DiscountDto create(@RequestBody DiscountDto dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public DiscountDto update(@PathVariable Long id, @RequestBody DiscountDto dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public DiscountDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<DiscountDto> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}