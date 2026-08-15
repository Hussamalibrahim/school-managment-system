package com.SchoolManagementSystem.system.utils.file;

import java.util.UUID;

public final class FileNameUtil {

    private FileNameUtil() {
    }

    public static String generate(String extension) {

        String uuid = UUID.randomUUID().toString();

        if (extension == null || extension.isBlank()) {
            return uuid;
        }

        return uuid + "." + extension;
    }

}