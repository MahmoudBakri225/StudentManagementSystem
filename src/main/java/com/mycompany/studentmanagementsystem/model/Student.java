package com.mycompany.studentmanagementsystem.model;

import com.mycompany.studentmanagementsystem.enums.StudentType;

public abstract class Student extends Person {

    private String major;
    private int yearLevel;
    private double gpa;

    // Constructor
    public Student(String firstName, String lastName, int age,
                   String email, String phone, String address, String gender,
                   String major, int yearLevel, double gpa) {
        super(firstName, lastName, age, email, phone, address, gender);
        setMajor(major);
        setYearLevel(yearLevel);
        setGpa(gpa);
    }

    // getter
    public String getMajor() {
        return major;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public double getGpa() {
        return gpa;
    }

    // setter
    public void setMajor(String major) {
        if (major == null || major.trim().isEmpty()) {
            throw new IllegalArgumentException("Major cannot be null or empty.");
        }
        this.major = major.trim();
    }

    public void setYearLevel(int yearLevel) {
        if (yearLevel < 1 || yearLevel > 6) {
            throw new IllegalArgumentException("Year level must be between 1 and 6.");
        }
        this.yearLevel = yearLevel;
    }

    public void setGpa(double gpa) {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0.");
        }
        this.gpa = gpa;
    }

    public abstract StudentType getStudentType();

    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public String toString() {
        return getStudentType() + " | ID: " + getId() + " | " + getFullName()
                + " | Major: " + major + " | Year: " + yearLevel
                + " | GPA: " + String.format("%.2f", gpa);
    }
}
