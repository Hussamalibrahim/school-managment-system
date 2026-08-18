package com.SchoolManagementSystem.system.entity.file;

import com.SchoolManagementSystem.system.entity.school.SchoolEntity;
import com.SchoolManagementSystem.system.entity.enumeration.FileOwnerType;
import com.SchoolManagementSystem.system.entity.enumeration.FileType;
import com.SchoolManagementSystem.system.entity.enumeration.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File extends SchoolEntity {

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "stored_name", nullable = false, unique = true)
    private String storedName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "extension")
    private String extension;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "file_size")
    private Long fileSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    private FileType fileType;

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_type", nullable = false)
    private FileOwnerType ownerType;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "uploaded_by_type")
    private UserType uploadedByType;

    @Column(name = "uploaded_by_id")
    private Long uploadedById;
}