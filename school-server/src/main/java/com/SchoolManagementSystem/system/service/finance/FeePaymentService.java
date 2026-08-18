package com.SchoolManagementSystem.system.service.finance;

import com.SchoolManagementSystem.system.dto.finance.request.FeePaymentRequest;
import com.SchoolManagementSystem.system.dto.finance.response.FeePaymentDto;
import com.SchoolManagementSystem.system.dto.finance.response.GuardianFeesResponse;

import java.util.List;

public interface FeePaymentService {

    FeePaymentDto save(Long feeId, FeePaymentRequest request);

    FeePaymentDto getById(Long id);

    List<FeePaymentDto> getByFee(Long feeId);

    List<FeePaymentDto> getByStudent(Long studentId);

    void delete(Long id);

    FeePaymentDto getByReceiptNumber(String receiptNumber);

    GuardianFeesResponse getGuardianFees(Long refId);
}
