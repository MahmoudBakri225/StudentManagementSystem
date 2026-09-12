package com.mycompany.studentmanagementsystem.model;

public class Grade {

    private final Student student;
    private final Course course;
    private double score;
    private String letterGrade;

    // Constructor
    public Grade(Student student, Course course, double score) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        this.student = student;
        this.course = course;
        setScore(score);
    }

    // getter
    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public double getScore() {
        return score;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    // setter
    public void setScore(double score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100.");
        }
        this.score = score;
        this.letterGrade = calculateLetterGrade(score);
    }

    private String calculateLetterGrade(double score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    @Override
    public String toString() {
        return student.getFullName() + " | " + course.getCourseName()
                + " | Score: " + score + " | Grade: " + letterGrade;
    }
}
