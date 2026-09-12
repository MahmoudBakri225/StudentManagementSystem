package com.mycompany.studentmanagementsystem.model;

import com.mycompany.studentmanagementsystem.enums.InstructorType;

public class FullTimeInstructor extends Instructor {

    private double monthlySalary;

    // Constructor
    public FullTimeInstructor(String firstName, String lastName, int age,
                              String email, String phone, String address, String gender,
                              String department, String specialization,
                              int yearsOfExperience, double monthlySalary) {
        super(firstName, lastName, age, email, phone, address, gender,
                department, specialization, yearsOfExperience);
        setMonthlySalary(monthlySalary);
    }

    // getter
    public double getMonthlySalary() {
        return monthlySalary;
    }

    // setter
    public void setMonthlySalary(double monthlySalary) {
        if (monthlySalary < 0) {
            throw new IllegalArgumentException("Monthly salary cannot be negative.");
        }
        this.monthlySalary = monthlySalary;
    }

    @Override
    public InstructorType getInstructorType() {
        return InstructorType.FULL_TIME;
    }

    @Override
    public String toString() {
        return super.toString() + " | Salary: " + String.format("%.2f", monthlySalary);
    }
}
