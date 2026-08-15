package com.SchoolManagementSystem.System.tenant;

public final class TenantContext {

    private static final ThreadLocal<Long> SCHOOL_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> SCHOOL_CODE = new ThreadLocal<>();

    private TenantContext() {
    }

    public static void set(Long schoolId, String schoolCode) {
        SCHOOL_ID.set(schoolId);
        SCHOOL_CODE.set(schoolCode);
    }
    public static void setSchoolCode(String schoolCode) {
        SCHOOL_CODE.set(schoolCode);
    }
    public static void setSchoolId(Long schoolId) {
        SCHOOL_ID.set(schoolId);
    }

    public static Long getSchoolId() {
        return SCHOOL_ID.get();
    }

    public static String getSchoolCode() {
        return SCHOOL_CODE.get();
    }

    public static boolean exists() {
        return SCHOOL_ID.get() != null;
    }

    public static void clear() {
        SCHOOL_ID.remove();
        SCHOOL_CODE.remove();
    }
}