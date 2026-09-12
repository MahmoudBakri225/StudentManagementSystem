package com.mycompany.studentmanagementsystem.model;

import java.time.LocalDate;

public class Attendance {

    private final Student student;
    private final Course course;
    private final LocalDate date;
    private final boolean present;

    // Constructor
    public Attendance(Student student, Course course, LocalDate date, boolean present) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }
        this.student = student;
        this.course = course;
        this.date = date;
        this.present = present;
    }

    // getter
    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isPresent() {
        return present;
    }

    @Override
    public String toString() {
        return student.getFullName() + " | " + course.getCourseName()
                + " | " + date + " | " + (present ? "Present" : "Absent");
    }
}
