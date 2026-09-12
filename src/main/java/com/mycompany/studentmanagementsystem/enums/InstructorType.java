package com.mycompany.studentmanagementsystem.enums;

public enum InstructorType {
    FULL_TIME("Full Time"),
    PART_TIME("Part Time");

    private final String displayName;

    InstructorType(String displayName) {
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
