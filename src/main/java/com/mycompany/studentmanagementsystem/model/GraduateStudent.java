package com.mycompany.studentmanagementsystem.model;

import com.mycompany.studentmanagementsystem.enums.StudentType;

public class GraduateStudent extends Student {

    private String researchArea;

    // Constructor
    public GraduateStudent(String firstName, String lastName, int age,
                           String email, String phone, String address, String gender,
                           String major, int yearLevel, double gpa, String researchArea) {
        super(firstName, lastName, age, email, phone, address, gender, major, yearLevel, gpa);
        setResearchArea(researchArea);
    }

    // getter
    public String getResearchArea() {
        return researchArea;
    }

    // setter
    public void setResearchArea(String researchArea) {
        if (researchArea == null || researchArea.trim().isEmpty()) {
            throw new IllegalArgumentException("Research area cannot be null or empty.");
        }
        this.researchArea = researchArea.trim();
    }

    @Override
    public StudentType getStudentType() {
        return StudentType.GRADUATE;
    }

    @Override
    public String toString() {
        return super.toString() + " | Research: " + researchArea;
    }
}
