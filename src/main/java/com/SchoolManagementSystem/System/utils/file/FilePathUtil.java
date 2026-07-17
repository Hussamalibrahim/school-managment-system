package com.SchoolManagementSystem.System.utils.file;

import com.SchoolManagementSystem.System.entity.enumeration.FileOwnerType;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class FilePathUtil {

    private FilePathUtil() {
    }

    public static Path buildPath(
            String root,
            FileOwnerType ownerType,
            Long ownerId
    ) {

        return Paths.get(
                root,
                ownerType.name().toLowerCase(),
                ownerId.toString()
        );

    }

}