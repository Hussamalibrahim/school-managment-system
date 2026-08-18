package com.SchoolManagementSystem.system.service.finance.impl;

import com.SchoolManagementSystem.system.entity.finance.Fee;
import com.SchoolManagementSystem.system.mapper.finance.ClassFeeMapper;
import com.SchoolManagementSystem.system.service.finance.FeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeeServiceImpl implements FeeService {
    @Override
    public Fee save(Fee dto) {
        return null;
    }

    @Override
    public Fee update(Long aLong, Fee dto) {
        return null;
    }

    @Override
    public Fee getById(Long aLong) {
        return null;
    }

    @Override
    public List<Fee> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long aLong) {

    }
}
