package com.SchoolManagementSystem.System.repository.finance;

import com.SchoolManagementSystem.System.entity.finance.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>
{
}