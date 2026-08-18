package com.SchoolManagementSystem.system.config;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FcmService {

    public void sendAnnouncementNotification(
            String topic,
            String title,
            String body,
            Long announcementId) {

        Notification notification =
                Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build();

        Message message = Message.builder()
                        .setTopic(topic)
                        .setNotification(notification)
                        .putData("type", "ANNOUNCEMENT")
                        .putData("announcementId", announcementId.toString())
                        .build();

        try {
            String response = FirebaseMessaging
                            .getInstance()
                            .send(message);

            log.info("FCM notification sent. topic={}, response={}", topic, response);

        } catch (Exception e) {
            log.error("Failed to send FCM notification. topic={}", topic, e);
        }
    }
    public void sendToTopic(
            String topic,
            String title,
            String body,
            String type) {

        Notification notification = Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build();

        Message message = Message.builder()
                        .setTopic(topic)
                        .setNotification(notification)
                        .putData("type", type)
                        .build();

        try {
            String response = FirebaseMessaging
                            .getInstance()
                            .send(message);

            log.info("FCM notification sent to topic {}: {}", topic, response);

        } catch (Exception e) {

            log.error("Failed to send FCM notification to topic {}", topic, e);
        }
    }
}