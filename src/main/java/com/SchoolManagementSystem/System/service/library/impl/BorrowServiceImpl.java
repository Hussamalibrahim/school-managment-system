package com.SchoolManagementSystem.System.service.library.impl;

import com.SchoolManagementSystem.System.dto.library.BorrowDto;
import com.SchoolManagementSystem.System.mapper.library.BorrowMapper;
import com.SchoolManagementSystem.System.entity.library.Borrow;
import com.SchoolManagementSystem.System.repository.library.BorrowRepository;
import com.SchoolManagementSystem.System.service.library.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRepository repository;

    @Override
    public BorrowDto save(BorrowDto dto) {
        Borrow borrow = BorrowMapper.toEntity(dto);
        borrow = repository.save(borrow);
        return BorrowMapper.toDto(borrow);
    }

    @Override
    public BorrowDto update(Long id, BorrowDto dto) {
        Borrow borrow = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrow not found"));

        borrow.setBorrowDate(dto.borrowDate());
        borrow.setDueDate(dto.dueDate());
        borrow.setReturnDate(dto.returnDate());

        borrow = repository.save(borrow);
        return BorrowMapper.toDto(borrow);
    }

    @Override
    public BorrowDto getById(Long id) {
        return repository.findById(id)
                .map(BorrowMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Borrow not found"));
    }

    @Override
    public List<BorrowDto> getAll() {
        return repository.findAll()
                .stream()
                .map(BorrowMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}