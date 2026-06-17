package com.SchoolManagementSystem.System.dto.file;

import com.SchoolManagementSystem.System.entity.enumeration.FileType;
import com.SchoolManagementSystem.System.entity.file.File;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link File}
 */
public record FileDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                      String fileName, String filePath, FileType fileType, String ownerType, Long ownerId,
                      String uploadedByType, Long uploadedById) implements Serializable {
}