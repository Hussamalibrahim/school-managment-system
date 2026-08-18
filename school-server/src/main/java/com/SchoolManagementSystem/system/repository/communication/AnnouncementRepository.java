package com.SchoolManagementSystem.system.repository.communication;

import com.SchoolManagementSystem.system.entity.communication.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findBySchoolIdAndActiveTrueOrderByCreatedAtDesc(Long schoolId);
}