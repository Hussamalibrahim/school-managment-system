package com.SchoolManagementSystem.System.repository.finance;

import com.SchoolManagementSystem.System.entity.finance.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long>
{
}