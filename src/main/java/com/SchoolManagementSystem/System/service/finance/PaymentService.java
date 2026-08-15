package com.SchoolManagementSystem.System.service.finance;

import com.SchoolManagementSystem.System.dto.finance.PaymentDto;
import com.SchoolManagementSystem.System.service.CrudService;

import java.util.List;

public interface PaymentService extends CrudService<PaymentDto, Long> {
    List<PaymentDto> getPaymentsByStudentId(Long studentId);
}
