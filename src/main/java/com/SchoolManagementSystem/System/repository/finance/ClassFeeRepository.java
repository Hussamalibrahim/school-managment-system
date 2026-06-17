package com.SchoolManagementSystem.System.repository.finance;

import com.SchoolManagementSystem.System.entity.finance.ClassFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassFeeRepository extends JpaRepository<ClassFee, Long>
{
}