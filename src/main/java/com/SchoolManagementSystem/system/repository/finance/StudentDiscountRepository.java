package com.SchoolManagementSystem.System.repository.finance;

import com.SchoolManagementSystem.System.entity.finance.StudentDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDiscountRepository extends JpaRepository<StudentDiscount, Long>
{
}