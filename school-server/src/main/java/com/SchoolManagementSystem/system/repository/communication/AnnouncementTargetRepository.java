package com.SchoolManagementSystem.system.repository.communication;

import com.SchoolManagementSystem.system.entity.communication.AnnouncementTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnnouncementTargetRepository extends JpaRepository<AnnouncementTarget, Long> {
    Optional<AnnouncementTarget> findByAnnouncementId(Long id);
}
