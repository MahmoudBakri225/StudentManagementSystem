package com.mycompany.studentmanagementsystem.enums;

public enum StudentType {
    UNDERGRADUATE("Undergraduate"),
    GRADUATE("Graduate");

    private final String displayName;

    StudentType(String displayName) {
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
