package com.SchoolManagementSystem.system.service.finance.impl;

import com.SchoolManagementSystem.system.dto.finance.PaymentDto;
import com.SchoolManagementSystem.system.mapper.finance.PaymentMapper;
import com.SchoolManagementSystem.system.entity.finance.Payment;
import com.SchoolManagementSystem.system.repository.finance.PaymentRepository;
import com.SchoolManagementSystem.system.service.finance.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    @Override
    public PaymentDto save(PaymentDto dto) {
        Payment payment = PaymentMapper.toEntity(dto);
        payment = repository.save(payment);
        return PaymentMapper.toDto(payment);
    }

    @Override
    public PaymentDto update(Long id, PaymentDto dto) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setAmount(dto.amount());
        payment.setPaymentDate(dto.paymentDate());
        payment.setNotes(dto.notes());

        payment = repository.save(payment);
        return PaymentMapper.toDto(payment);
    }

    @Override
    public PaymentDto getById(Long id) {
        return repository.findById(id)
                .map(PaymentMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Override
    public List<PaymentDto> getAll() {
        return repository.findAll()
                .stream()
                .map(PaymentMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}