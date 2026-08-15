package com.SchoolManagementSystem.System.service.finance.impl;

import com.SchoolManagementSystem.System.dto.finance.PaymentDto;
import com.SchoolManagementSystem.System.entity.finance.Payment;
import com.SchoolManagementSystem.System.entity.student.Student;
import com.SchoolManagementSystem.System.exception.business.NotFoundException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.mapper.finance.PaymentMapper;
import com.SchoolManagementSystem.System.repository.finance.PaymentRepository;
import com.SchoolManagementSystem.System.repository.student.StudentRepository;
import com.SchoolManagementSystem.System.service.finance.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final StudentRepository studentRepository;

    @Override
    public PaymentDto save(PaymentDto dto) {
        Payment payment = PaymentMapper.toEntity(dto);
        if (dto.studentId() != null) {
            Student student = studentRepository.findById(dto.studentId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.STUDENT_NOT_FOUND));
            payment.setStudent(student);
        }
        payment = repository.save(payment);
        return PaymentMapper.toDto(payment);
    }

    @Override
    public PaymentDto update(Long id, PaymentDto dto) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PAYMENT_NOT_FOUND));

        payment.setAmount(dto.amount());
        payment.setPaymentDate(dto.paymentDate());
        payment.setNotes(dto.notes());
        if (dto.studentId() != null) {
            Student student = studentRepository.findById(dto.studentId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.STUDENT_NOT_FOUND));
            payment.setStudent(student);
        }

        payment = repository.save(payment);
        return PaymentMapper.toDto(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDto getById(Long id) {
        return repository.findById(id)
                .map(PaymentMapper::toDto)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PAYMENT_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
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

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentsByStudentId(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new NotFoundException(ErrorCode.STUDENT_NOT_FOUND);
        }
        return repository.findByStudentId(studentId)
                .stream()
                .map(PaymentMapper::toDto)
                .toList();
    }
}