package com.SchoolManagementSystem.system.repository.finance;

import com.SchoolManagementSystem.system.entity.finance.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {

    List<FeePayment> findByFeeIdOrderByPaymentDateDesc(Long feeId);

    List<FeePayment> findByFeeStudentIdOrderByPaymentDateDesc(Long studentId);

    boolean existsByReceiptNumber(String receiptNumber);

    Optional<FeePayment> findByReceiptNumber(String receiptNumber);
}
