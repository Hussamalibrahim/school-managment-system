package com.SchoolManagementSystem.system.controller.tenant.finance;

import com.SchoolManagementSystem.system.dto.finance.ClassFeeDto;
import com.SchoolManagementSystem.system.service.finance.ClassFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-fees")
@RequiredArgsConstructor
public class ClassFeeController {

    private final ClassFeeService service;

    @PostMapping
    public ClassFeeDto create(@RequestBody ClassFeeDto dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public ClassFeeDto update(@PathVariable Long id, @RequestBody ClassFeeDto dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public ClassFeeDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<ClassFeeDto> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}