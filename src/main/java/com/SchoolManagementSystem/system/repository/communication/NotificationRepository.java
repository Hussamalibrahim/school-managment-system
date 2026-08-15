package com.SchoolManagementSystem.System.repository.communication;

import com.SchoolManagementSystem.System.entity.communication.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>
{
}