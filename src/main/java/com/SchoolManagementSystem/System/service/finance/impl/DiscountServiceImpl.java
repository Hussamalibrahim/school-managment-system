package com.SchoolManagementSystem.System.service.finance.impl;

import com.SchoolManagementSystem.System.dto.finance.DiscountDto;
import com.SchoolManagementSystem.System.dtoMapper.finance.DiscountMapper;
import com.SchoolManagementSystem.System.entity.finance.Discount;
import com.SchoolManagementSystem.System.repository.finance.DiscountRepository;
import com.SchoolManagementSystem.System.service.finance.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository repository;

    @Override
    public DiscountDto save(DiscountDto dto) {
        Discount discount = DiscountMapper.toEntity(dto);
        discount = repository.save(discount);
        return DiscountMapper.toDto(discount);
    }

    @Override
    public DiscountDto update(Long id, DiscountDto dto) {
        Discount discount = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found"));

        discount.setName(dto.name());
        discount.setPercentage(dto.percentage());
        discount.setReason(dto.reason());

        discount = repository.save(discount);
        return DiscountMapper.toDto(discount);
    }

    @Override
    public DiscountDto getById(Long id) {
        return repository.findById(id)
                .map(DiscountMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Discount not found"));
    }

    @Override
    public List<DiscountDto> getAll() {
        return repository.findAll()
                .stream()
                .map(DiscountMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}