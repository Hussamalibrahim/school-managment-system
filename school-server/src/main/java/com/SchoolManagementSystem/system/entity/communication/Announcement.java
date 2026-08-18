package com.SchoolManagementSystem.system.entity.communication;

import com.SchoolManagementSystem.system.entity.Auth.AuthUser;
import com.SchoolManagementSystem.system.entity.school.School;
import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import com.SchoolManagementSystem.system.entity.enumeration.AnnouncementStatus;
import com.SchoolManagementSystem.system.entity.enumeration.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Announcement extends SchoolEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private AuthUser createdBy;

    @Column(name = "active", nullable = false)
    private Boolean active = true;
}