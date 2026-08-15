package com.SchoolManagementSystem.system.mapper.file;

import com.SchoolManagementSystem.system.dto.file.FileDto;
import com.SchoolManagementSystem.system.entity.file.File;

public final class FileMapper {

    private FileMapper() {
    }

    public static FileDto toDto(File file) {

        if (file == null) return null;

        return new FileDto(

                file.getId(),
                file.getCreatedAt(),
                file.getUpdatedAt(),
                file.getDeletedAt(),

                file.getOriginalName(),
                file.getStoredName(),
                file.getFilePath(),
                file.getExtension(),
                file.getMimeType(),
                file.getFileSize(),

                file.getFileType(),

                file.getOwnerType(),
                file.getOwnerId(),

                file.getUploadedByType(),
                file.getUploadedById()
        );
    }

    public static File toEntity(FileDto dto) {

        if (dto == null) return null;

        File file = new File();

        file.setId(dto.id());
        file.setCreatedAt(dto.createdAt());
        file.setUpdatedAt(dto.updatedAt());
        file.setDeletedAt(dto.deletedAt());

        file.setOriginalName(dto.originalName());
        file.setStoredName(dto.storedName());
        file.setFilePath(dto.filePath());
        file.setExtension(dto.extension());
        file.setMimeType(dto.mimeType());
        file.setFileSize(dto.fileSize());

        file.setFileType(dto.fileType());

        file.setOwnerType(dto.ownerType());
        file.setOwnerId(dto.ownerId());

        file.setUploadedByType(dto.uploadedByType());
        file.setUploadedById(dto.uploadedById());

        return file;
    }
}