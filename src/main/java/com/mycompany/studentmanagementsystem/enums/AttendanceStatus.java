package com.mycompany.studentmanagementsystem.enums;

public enum AttendanceStatus {
    PRESENT("Present"),
    ABSENT("Absent");

    private final String displayName;

    AttendanceStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
