package com.SchoolManagementSystem.System.dto.file.response;

import org.springframework.core.io.Resource;

public record DownloadFileResponse(

        Resource resource,

        String originalName,

        String mimeType,

        Long fileSize

) {
}