package com.SchoolManagementSystem.system.entity.communication;

import com.SchoolManagementSystem.system.entity.enumeration.AnnouncementTargetType;
import com.SchoolManagementSystem.system.entity.enumeration.Role;
import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "announcement_targets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementTarget extends SchoolEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    @Enumerated(EnumType.STRING)
    @Column(name ="target_type", nullable = false)
    private AnnouncementTargetType type;

    @Enumerated(EnumType.STRING)
    @Column(name ="target_role")
    private Role targetRole;

    @Column(name = "target_id")
    private Long targetId;
}