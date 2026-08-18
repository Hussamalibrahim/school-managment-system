package com.SchoolManagementSystem.system.service.finance.impl;

import com.SchoolManagementSystem.system.dto.finance.request.DiscountRequest;
import com.SchoolManagementSystem.system.dto.finance.DiscountDto;
import com.SchoolManagementSystem.system.entity.enumeration.DiscountType;
import com.SchoolManagementSystem.system.entity.finance.Discount;
import com.SchoolManagementSystem.system.entity.finance.Fee;
import com.SchoolManagementSystem.system.exception.business.AlreadyExistsException;
import com.SchoolManagementSystem.system.exception.business.NotFoundException;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.mapper.finance.DiscountMapper;
import com.SchoolManagementSystem.system.repository.finance.DiscountRepository;
import com.SchoolManagementSystem.system.repository.finance.FeeRepository;
import com.SchoolManagementSystem.system.service.finance.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
@Transactional
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final FeeRepository feeRepository;

    @Override
    public DiscountDto apply(Long feeId, DiscountRequest request) {

        Fee fee = feeRepository.findById(feeId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FEE_NOT_FOUND));

        if (discountRepository.existsByFeeId(feeId)) {

            throw new AlreadyExistsException(ErrorCode.DISCOUNT_ALREADY_EXISTS);
        }

        validate(request, fee);

        Discount discount = DiscountMapper.toEntity(request, fee);

        discount = discountRepository.save(discount);

        return DiscountMapper.toDto(discount);
    }

    @Override
    @Transactional(readOnly = true)
    public DiscountDto getByFee(Long feeId) {

        Discount discount = discountRepository.findByFeeId(feeId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DISCOUNT_NOT_FOUND));

        return DiscountMapper.toDto(discount);
    }

    @Override
    public void delete(Long feeId) {
        Discount discount = discountRepository.findByFeeId(feeId)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.DISCOUNT_NOT_FOUND));

        discountRepository.delete(discount);
    }

    private void validate(DiscountRequest request, Fee fee) {

        if (request.name() == null || request.name().isBlank()) {

            throw new ValidationException(ErrorCode.INVALID_DISCOUNT);
        }

        if (request.discountType() == null || request.value() == null ||
                request.value().compareTo(BigDecimal.ZERO) <= 0) {

            throw new ValidationException(ErrorCode.INVALID_DISCOUNT);
        }

        if (request.discountType().equals(DiscountType.PERCENTAGE)) {
            if (request.value().compareTo(BigDecimal.valueOf(100)) > 0) {

                throw new ValidationException(ErrorCode.INVALID_DISCOUNT);
            }
        } else {

            if (request.value().compareTo(fee.getAmount()) > 0) {
                throw new ValidationException(ErrorCode.INVALID_DISCOUNT);}
        }
    }

    @Override
    public List<DiscountDto> getAll() {
        return discountRepository.findAll()
                .stream()
                .map(DiscountMapper::toDto)
                .toList();
    }
}