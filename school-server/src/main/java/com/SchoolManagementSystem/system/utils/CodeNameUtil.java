package com.SchoolManagementSystem.system.utils;

import com.SchoolManagementSystem.system.exception.business.ValidationException;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;

import java.text.Normalizer;
import java.util.Locale;

public final class CodeNameUtil {

    private CodeNameUtil() {
    }

    public static String generateCode(String name) {

        if (name == null || name.isBlank()) {
            throw new ValidationException(ErrorCode.CANT_NAME_BE_EMPTY);
        }

        return Normalizer
                .normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase(Locale.ENGLISH)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }
}