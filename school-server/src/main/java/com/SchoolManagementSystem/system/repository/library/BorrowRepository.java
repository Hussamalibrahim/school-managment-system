package com.SchoolManagementSystem.system.repository.library;

import com.SchoolManagementSystem.system.entity.library.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long>
{
}