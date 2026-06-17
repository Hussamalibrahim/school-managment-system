package com.SchoolManagementSystem.System.repository.school;

import com.SchoolManagementSystem.System.entity.school.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long>
{
}