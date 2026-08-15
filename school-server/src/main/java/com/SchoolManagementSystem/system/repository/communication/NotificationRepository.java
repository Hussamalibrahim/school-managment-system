package com.SchoolManagementSystem.system.repository.communication;

import com.SchoolManagementSystem.system.entity.communication.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>
{
}