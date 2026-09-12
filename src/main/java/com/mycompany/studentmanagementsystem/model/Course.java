package com.mycompany.studentmanagementsystem.model;

import com.mycompany.studentmanagementsystem.exception.CourseEnrollmentException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {

    private final String courseId;
    private String courseName;
    private Instructor instructor;
    private static final int MAX_STUDENTS = 50;
    private final List<Student> enrolledStudents;

    // Constructor
    public Course(String courseId, String courseName, Instructor instructor) {
        if (courseId == null || courseId.trim().isEmpty()) {
            throw new IllegalArgumentException("Course ID cannot be null or empty.");
        }
        this.courseId = courseId.trim();
        setCourseName(courseName);
        setInstructor(instructor);
        this.enrolledStudents = new ArrayList<>();
    }

    public boolean enrollStudent(Student student) throws CourseEnrollmentException {
        if (student == null) {
            throw new CourseEnrollmentException("Student cannot be null.");
        }
        if (enrolledStudents.size() >= MAX_STUDENTS) {
            throw new CourseEnrollmentException(
                    "Cannot enroll. Course reached maximum capacity of " + MAX_STUDENTS + " students.");
        }
        if (enrolledStudents.contains(student)) {
            throw new CourseEnrollmentException("Student is already enrolled in this course.");
        }
        enrolledStudents.add(student);
        return true;
    }

    public boolean removeStudent(Student student) {
        return enrolledStudents.remove(student);
    }

    public boolean isStudentEnrolled(Student student) {
        return enrolledStudents.contains(student);
    }

    // getter
    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public int getMaxStudents() {
        return MAX_STUDENTS;
    }

    // Defensive copy: callers cannot mutate the internal roster directly.
    public List<Student> getEnrolledStudents() {
        return new ArrayList<>(enrolledStudents);
    }

    public int getCurrentEnrollment() {
        return enrolledStudents.size();
    }

    // setter
    public void setCourseName(String courseName) {
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty.");
        }
        this.courseName = courseName.trim();
    }

    public void setInstructor(Instructor instructor) {
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor cannot be null.");
        }
        this.instructor = instructor;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course other = (Course) obj;
        return Objects.equals(courseId, other.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }

    @Override
    public String toString() {
        return "Course{" + courseId + " | " + courseName
                + " | Instructor: " + instructor.getFullName()
                + " | Enrolled: " + enrolledStudents.size() + "/" + MAX_STUDENTS + "}";
    }
}
