package com.mycompany.studentmanagementsystem.model;

import com.mycompany.studentmanagementsystem.enums.InstructorType;

public abstract class Instructor extends Person {

    private String department;
    private String specialization;
    private int yearsOfExperience;

    // Constructor
    public Instructor(String firstName, String lastName, int age,
                      String email, String phone, String address, String gender,
                      String department, String specialization, int yearsOfExperience) {
        super(firstName, lastName, age, email, phone, address, gender);
        setDepartment(department);
        setSpecialization(specialization);
        setYearsOfExperience(yearsOfExperience);
    }

    // getter
    public String getDepartment() {
        return department;
    }

    public String getSpecialization() {
        return specialization;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    // setter
    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be null or empty.");
        }
        this.department = department.trim();
    }

    public void setSpecialization(String specialization) {
        if (specialization == null || specialization.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialization cannot be null or empty.");
        }
        this.specialization = specialization.trim();
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        if (yearsOfExperience < 0) {
            throw new IllegalArgumentException("Years of experience cannot be negative.");
        }
        this.yearsOfExperience = yearsOfExperience;
    }

    public abstract InstructorType getInstructorType();

    @Override
    public String getRole() {
        return "Instructor";
    }

    @Override
    public String toString() {
        return getInstructorType() + " | ID: " + getId() + " | " + getFullName()
                + " | Dept: " + department + " | Spec: " + specialization
                + " | Exp: " + yearsOfExperience + " years";
    }
}
