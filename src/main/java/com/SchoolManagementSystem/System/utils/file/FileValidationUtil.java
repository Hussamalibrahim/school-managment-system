package com.SchoolManagementSystem.System.utils.file;

import com.SchoolManagementSystem.System.entity.enumeration.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public final class FileValidationUtil {

    private FileValidationUtil() {
    }

    private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024;
    private static final long MAX_DOCUMENT_SIZE = 20 * 1024 * 1024;

    private static final List<String> IMAGE_TYPES = List.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private static final List<String> DOCUMENT_TYPES = List.of(
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    public static void validate(
            MultipartFile file,
            FileType fileType
    ) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty.");
        }

        switch (fileType) {

            case IMAGE -> {

                if (!IMAGE_TYPES.contains(file.getContentType())) {
                    throw new RuntimeException("Invalid image type.");
                }

                if (file.getSize() > MAX_IMAGE_SIZE) {
                    throw new RuntimeException("Image size exceeded.");
                }

            }

            case DOCUMENT -> {

                if (!DOCUMENT_TYPES.contains(file.getContentType())) {
                    throw new RuntimeException("Invalid document type.");
                }

                if (file.getSize() > MAX_DOCUMENT_SIZE) {
                    throw new RuntimeException("Document size exceeded.");
                }

            }

            default -> {
            }

        }

    }

}