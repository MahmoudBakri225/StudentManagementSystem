package com.mycompany.studentmanagementsystem.model;

import com.mycompany.studentmanagementsystem.enums.InstructorType;

public class PartTimeInstructor extends Instructor {

    private double hourlyRate;
    private int workingHours;

    // Constructor
    public PartTimeInstructor(String firstName, String lastName, int age,
                              String email, String phone, String address, String gender,
                              String department, String specialization,
                              int yearsOfExperience, double hourlyRate, int workingHours) {
        super(firstName, lastName, age, email, phone, address, gender,
                department, specialization, yearsOfExperience);
        setHourlyRate(hourlyRate);
        setWorkingHours(workingHours);
    }

    // getter
    public double getHourlyRate() {
        return hourlyRate;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    // setter
    public void setHourlyRate(double hourlyRate) {
        if (hourlyRate < 0) {
            throw new IllegalArgumentException("Hourly rate cannot be negative.");
        }
        this.hourlyRate = hourlyRate;
    }

    public void setWorkingHours(int workingHours) {
        if (workingHours < 0) {
            throw new IllegalArgumentException("Working hours cannot be negative.");
        }
        this.workingHours = workingHours;
    }

    public double calculateMonthlyPay() {
        return hourlyRate * workingHours;
    }

    @Override
    public InstructorType getInstructorType() {
        return InstructorType.PART_TIME;
    }

    @Override
    public String toString() {
        return super.toString() + " | Rate: " + String.format("%.2f", hourlyRate)
                + "/hr | Hours: " + workingHours
                + " | Monthly Pay: " + String.format("%.2f", calculateMonthlyPay());
    }
}
