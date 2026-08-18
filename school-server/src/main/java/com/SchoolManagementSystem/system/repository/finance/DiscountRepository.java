package com.SchoolManagementSystem.system.repository.finance;

import com.SchoolManagementSystem.system.entity.finance.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    boolean existsByFeeId(Long feeId);

    Optional<Discount> findByFeeId(Long feeId);
}
