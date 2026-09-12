package com.mycompany.studentmanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

public class Department {

    private String departmentName;
    private final List<Student> students;
    private final List<Course> courses;

    // Constructor
    public Department(String departmentName) {
        setDepartmentName(departmentName);
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    // getter
    public String getDepartmentName() {
        return departmentName;
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    // setter
    public void setDepartmentName(String departmentName) {
        if (departmentName == null || departmentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be null or empty.");
        }
        this.departmentName = departmentName.trim();
    }

    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    public boolean removeStudent(Student student) {
        return students.remove(student);
    }

    public void addCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public boolean removeCourse(Course course) {
        return courses.remove(course);
    }

    public int getStudentCount() {
        return students.size();
    }

    public int getCourseCount() {
        return courses.size();
    }

    @Override
    public String toString() {
        return "Department{" + departmentName
                + " | Students: " + students.size()
                + " | Courses: " + courses.size() + "}";
    }
}
