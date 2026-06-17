package com.SchoolManagementSystem.System.controller.finance;

import com.SchoolManagementSystem.System.dto.finance.StudentDiscountDto;
import com.SchoolManagementSystem.System.service.finance.StudentDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-discounts")
@RequiredArgsConstructor
public class StudentDiscountController {

    private final StudentDiscountService service;

    @PostMapping
    public StudentDiscountDto create(@RequestBody StudentDiscountDto dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public StudentDiscountDto update(@PathVariable Long id, @RequestBody StudentDiscountDto dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public StudentDiscountDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<StudentDiscountDto> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}