package com.SchoolManagementSystem.System.repository.finance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SchoolManagementSystem.System.entity.finance.FeeType;

@Repository
public interface FeeTypeRepository extends JpaRepository<FeeType, Long>
{
}