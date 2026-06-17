package com.SchoolManagementSystem.System.repository.school;

import com.SchoolManagementSystem.System.entity.school.SchoolSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolSettingsRepository extends JpaRepository<SchoolSettings, Long>
{
}