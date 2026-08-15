package com.SchoolManagementSystem.system.dto.file;

import com.SchoolManagementSystem.system.entity.enumeration.FileType;
import com.SchoolManagementSystem.system.entity.file.File;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link File}
 */

import com.SchoolManagementSystem.system.entity.enumeration.FileOwnerType;
import com.SchoolManagementSystem.system.entity.enumeration.UserType;

public record FileDto(

        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,

        String originalName,
        String storedName,
        String filePath,
        String extension,
        String mimeType,
        Long fileSize,

        FileType fileType,

        FileOwnerType ownerType,
        Long ownerId,

        UserType uploadedByType,
        Long uploadedById

) implements Serializable {
}