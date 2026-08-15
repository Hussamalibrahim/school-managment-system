package com.SchoolManagementSystem.system.repository.finance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SchoolManagementSystem.system.entity.finance.FeeType;

@Repository
public interface FeeTypeRepository extends JpaRepository<FeeType, Long>
{
}