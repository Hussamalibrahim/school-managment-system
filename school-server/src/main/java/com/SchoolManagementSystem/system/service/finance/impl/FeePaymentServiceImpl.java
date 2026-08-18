package com.SchoolManagementSystem.system.service.finance.impl;

import com.SchoolManagementSystem.system.dto.finance.GuardianStudentFeesDto;
import com.SchoolManagementSystem.system.dto.finance.StudentFeeDto;
import com.SchoolManagementSystem.system.dto.finance.request.FeePaymentRequest;
import com.SchoolManagementSystem.system.dto.finance.response.FeePaymentDto;
import com.SchoolManagementSystem.system.dto.finance.response.GuardianFeesResponse;
import com.SchoolManagementSystem.system.entity.finance.Fee;
import com.SchoolManagementSystem.system.entity.finance.FeePayment;
import com.SchoolManagementSystem.system.entity.student.Student;
import com.SchoolManagementSystem.system.entity.student.StudentGuardian;
import com.SchoolManagementSystem.system.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.finance.FeePaymentMapper;
import com.SchoolManagementSystem.system.repository.finance.FeePaymentRepository;
import com.SchoolManagementSystem.system.repository.finance.FeeRepository;
import com.SchoolManagementSystem.system.repository.student.StudentGuardianRepository;
import com.SchoolManagementSystem.system.service.finance.FeePaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeePaymentServiceImpl implements FeePaymentService {

    private final FeePaymentRepository feePaymentRepository;
    private final StudentGuardianRepository studentGuardianRepository;
    private final FeeRepository feeRepository;

    @Override
    public FeePaymentDto save(Long feeId, FeePaymentRequest request) {

        Fee fee = feeRepository.findById(feeId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FEE_NOT_FOUND));

        validatePayment(request);

        BigDecimal requiredAmount = FeePaymentMapper.calculateRequiredAmount(fee);

        BigDecimal paidAmount = getPaidAmount(feeId);

        if (paidAmount.compareTo(requiredAmount) >= 0) {
            throw new ValidationException(ErrorCode.FEE_ALREADY_PAID);
        }

        BigDecimal remainingAmount = requiredAmount.subtract(paidAmount);

        if (request.amount().compareTo(remainingAmount) > 0) {
            throw new ValidationException(ErrorCode.PAYMENT_EXCEEDS_REMAINING);
        }

        if (request.receiptNumber() != null
                && !request.receiptNumber().isBlank()
                && feePaymentRepository.existsByReceiptNumber(
                request.receiptNumber())) {

            throw new AlreadyExistsException(ErrorCode.RECEIPT_NUMBER_ALREADY_EXISTS);
        }

        FeePayment payment = new FeePayment();

        payment.setFee(fee);
        payment.setAmount(request.amount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(request.paymentMethod());
        payment.setReceiptNumber(request.receiptNumber());

        payment = feePaymentRepository.save(payment);

        BigDecimal newPaidAmount = paidAmount.add(request.amount());

        return FeePaymentMapper.toDto(payment, newPaidAmount);
    }

    @Override
    @Transactional(readOnly = true)
    public FeePaymentDto getById(Long id) {

        FeePayment payment = feePaymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FEE_PAYMENT_NOT_FOUND));

        BigDecimal paidAmount = getPaidAmount(payment.getFee().getId());

        return FeePaymentMapper.toDto(payment, paidAmount);
    }

    @Override
    @Transactional(readOnly = true)
    public FeePaymentDto getByReceiptNumber(String receiptNumber) {

        FeePayment payment = feePaymentRepository.findByReceiptNumber(receiptNumber)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FEE_PAYMENT_NOT_FOUND));

        BigDecimal paidAmount = getPaidAmount(payment.getFee().getId());

        return FeePaymentMapper.toDto(payment, paidAmount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeePaymentDto> getByFee(Long feeId) {

        feeRepository.findById(feeId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FEE_NOT_FOUND));

        BigDecimal paidAmount =
                getPaidAmount(feeId);

        return feePaymentRepository
                .findByFeeIdOrderByPaymentDateDesc(feeId)
                .stream()
                .map(payment -> FeePaymentMapper.toDto(payment, paidAmount)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeePaymentDto> getByStudent(Long studentId) {

        List<FeePayment> payments = feePaymentRepository.findByFeeStudentIdOrderByPaymentDateDesc(studentId);

        return payments.stream().map(payment -> {

                    BigDecimal paidAmount = getPaidAmount(payment.getFee().getId());

                    return FeePaymentMapper.toDto(payment, paidAmount);
                })
                .toList();
    }

    @Override
    public void delete(Long id) {

        FeePayment payment = feePaymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FEE_PAYMENT_NOT_FOUND));

        feePaymentRepository.delete(payment);
    }

    private BigDecimal getPaidAmount(Long feeId) {

        return feePaymentRepository
                .findByFeeIdOrderByPaymentDateDesc(feeId)
                .stream()
                .map(FeePayment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void validatePayment(FeePaymentRequest request) {

        if (request.amount() == null || request.amount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new ValidationException(ErrorCode.INVALID_PAYMENT_AMOUNT);
        }

        if (request.paymentMethod() == null) {

            throw new ValidationException(ErrorCode.INVALID_PAYMENT_METHOD);
        }
    }
    @Override
    @Transactional(readOnly = true)
    public GuardianFeesResponse getGuardianFees(Long guardianId) {

        List<StudentGuardian> relations = studentGuardianRepository.findByGuardianId(guardianId);

        BigDecimal totalRequired = BigDecimal.ZERO;
        BigDecimal totalPaid = BigDecimal.ZERO;
        BigDecimal totalRemaining = BigDecimal.ZERO;

        List<GuardianStudentFeesDto> students = new ArrayList<>();

        for (StudentGuardian relation : relations) {

            Student student = relation.getStudent();
            List<Fee> fees = feeRepository.findByStudentId(student.getId());

            List<StudentFeeDto> studentFees = new ArrayList<>();

            BigDecimal studentRequired = BigDecimal.ZERO;
            BigDecimal studentPaid = BigDecimal.ZERO;
            BigDecimal studentRemaining = BigDecimal.ZERO;

            for (Fee fee : fees) {
                BigDecimal requiredAmount =
                        FeePaymentMapper.calculateRequiredAmount(fee);
                BigDecimal paidAmount = feePaymentRepository
                        .findByFeeIdOrderByPaymentDateDesc(fee.getId())
                                .stream()
                                .map(FeePayment::getAmount)
                                .reduce(BigDecimal.ZERO,
                                        BigDecimal::add);

                BigDecimal remainingAmount = requiredAmount.subtract(paidAmount);

                BigDecimal discount = fee.getDiscount() != null
                                ? fee.getAmount()
                                .subtract(requiredAmount)
                                : BigDecimal.ZERO;

                studentFees.add(new StudentFeeDto(
                                fee.getId(),
                                fee.getAmount(),
                                discount,
                                requiredAmount,
                                paidAmount,
                                remainingAmount,
                                fee.getDueDate()));

                studentRequired = studentRequired.add(requiredAmount);
                studentPaid = studentPaid.add(paidAmount);
                studentRemaining = studentRemaining.add(remainingAmount);
            }

            students.add(new GuardianStudentFeesDto(
                            student.getId(),
                            student.getFirstName() + " " + student.getLastName(),
                            studentFees,
                            studentRequired,
                            studentPaid,
                            studentRemaining
                    )
            );

            totalRequired = totalRequired.add(studentRequired);
            totalPaid = totalPaid.add(studentPaid);
            totalRemaining = totalRemaining.add(studentRemaining);
        }

        return new GuardianFeesResponse(students, totalRequired, totalPaid, totalRemaining);
    }
}