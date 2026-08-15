package com.SchoolManagementSystem.System.dto.file;

import com.SchoolManagementSystem.System.entity.enumeration.FileType;
import com.SchoolManagementSystem.System.entity.file.File;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link File}
 */

import com.SchoolManagementSystem.System.entity.enumeration.FileOwnerType;
import com.SchoolManagementSystem.System.entity.enumeration.UserType;

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