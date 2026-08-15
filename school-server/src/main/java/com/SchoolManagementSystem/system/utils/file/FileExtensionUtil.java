package com.SchoolManagementSystem.system.utils.file;

public final class FileExtensionUtil {

    private FileExtensionUtil() {
    }

    public static String getExtension(String filename) {

        if (filename == null || !filename.contains(".")) {
            return "";
        }

        return filename.substring(
                filename.lastIndexOf('.') + 1
        );
    }

}