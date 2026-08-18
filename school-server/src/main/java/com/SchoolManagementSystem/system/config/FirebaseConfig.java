package com.SchoolManagementSystem.system.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Value("${GOOGLE_APPLICATION_CREDENTIALS}")
    private ClassPathResource resource;

    @PostConstruct
    public void initialize() throws IOException {

        if (!FirebaseApp.getApps().isEmpty()) {
            return;
        }

        FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                        .build();

        FirebaseApp.initializeApp(options);
    }
}