package com.SchoolManagementSystem.system.service.finance;

import com.SchoolManagementSystem.system.dto.finance.DiscountDto;
import com.SchoolManagementSystem.system.dto.finance.request.DiscountRequest;

import java.util.List;

public interface DiscountService {
    DiscountDto apply(Long feeId, DiscountRequest request);

    DiscountDto getByFee(Long feeId);

    void delete(Long feeId);

    List<DiscountDto> getAll();
}
