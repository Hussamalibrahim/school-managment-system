package com.SchoolManagementSystem.System.dtoMapper.file;

import com.SchoolManagementSystem.System.dto.file.FileDto;
import com.SchoolManagementSystem.System.entity.file.File;

public final class FileMapper {
    private FileMapper(){}
    public static FileDto toDto(File file) {
        if (file == null) return null;

        return new FileDto(
                file.getId(),
                file.getCreatedAt(),
                file.getUpdatedAt(),
                file.getDeletedAt(),
                file.getFileName(),
                file.getFilePath(),
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
        file.setFileName(dto.fileName());
        file.setFilePath(dto.filePath());
        file.setFileType(dto.fileType());
        file.setOwnerType(dto.ownerType());
        file.setOwnerId(dto.ownerId());
        file.setUploadedByType(dto.uploadedByType());
        file.setUploadedById(dto.uploadedById());

        return file;
    }
}