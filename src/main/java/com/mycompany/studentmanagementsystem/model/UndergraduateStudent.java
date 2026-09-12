package com.mycompany.studentmanagementsystem.model;

import com.mycompany.studentmanagementsystem.enums.StudentType;

public class UndergraduateStudent extends Student {

    // Constructor
    public UndergraduateStudent(String firstName, String lastName, int age,
                                String email, String phone, String address, String gender,
                                String major, int yearLevel, double gpa) {
        super(firstName, lastName, age, email, phone, address, gender, major, yearLevel, gpa);
    }

    @Override
    public StudentType getStudentType() {
        return StudentType.UNDERGRADUATE;
    }
}
