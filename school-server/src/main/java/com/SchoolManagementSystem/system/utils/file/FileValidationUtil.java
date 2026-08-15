package com.SchoolManagementSystem.system.utils.file;

import com.SchoolManagementSystem.system.entity.enumeration.FileType;
import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

public final class FileValidationUtil {

    private FileValidationUtil() {
    }

    @Value("${storage.max-image-size}")
    private static long MAX_IMAGE_SIZE;

    @Value("${storage.max-document-size}")
    private static long MAX_DOCUMENT_SIZE;

    @Value("${storage.image-types}")
    private static List<String> IMAGE_TYPES;

    @Value("${storage.document-types}")
    private static List<String> DOCUMENT_TYPES;

    public static void validate(
            MultipartFile file,
            FileType fileType
    ) {

        if (file == null || file.isEmpty()) {
            throw new ValidationException(ErrorCode.FILE_EMPTY);
        }

        switch (fileType) {

            case IMAGE -> {

                if (!IMAGE_TYPES.contains(file.getContentType())) {
                    throw new ValidationException(ErrorCode.INVALID_IMAGE_TYPE);
                }

                if (file.getSize() > MAX_IMAGE_SIZE) {
                    throw new ValidationException(ErrorCode.IMAGE_SIZE_EXCEEDED);
                }
            }

            case DOCUMENT -> {

                if (!DOCUMENT_TYPES.contains(file.getContentType())) {
                    throw new ValidationException(ErrorCode.INVALID_DOCUMENT_TYPE);
                }

                if (file.getSize() > MAX_DOCUMENT_SIZE) {
                    throw new ValidationException(ErrorCode.DOCUMENT_SIZE_EXCEEDED);
                }
            }

            default -> {
            }
        }
    }
}