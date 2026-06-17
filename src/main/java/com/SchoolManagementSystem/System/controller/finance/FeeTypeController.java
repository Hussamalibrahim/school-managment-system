package com.SchoolManagementSystem.System.controller.finance;

import com.SchoolManagementSystem.System.dto.finance.FeeTypeDto;
import com.SchoolManagementSystem.System.service.finance.FeeTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fee-types")
@RequiredArgsConstructor
public class FeeTypeController {

    private final FeeTypeService service;

    @PostMapping
    public FeeTypeDto create(@RequestBody FeeTypeDto dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public FeeTypeDto update(@PathVariable Long id, @RequestBody FeeTypeDto dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public FeeTypeDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<FeeTypeDto> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}