package com.SchoolManagementSystem.system.entity.school;

import com.SchoolManagementSystem.system.entity.enumeration.SchoolRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "school_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolRequest extends SchoolEntity {

    @OneToOne(optional = false)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SchoolRequestStatus status;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "reviewed_by")
    private Long reviewedBy;
}
