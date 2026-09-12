package com.mycompany.studentmanagementsystem.service;

import com.mycompany.studentmanagementsystem.exception.CourseEnrollmentException;
import com.mycompany.studentmanagementsystem.model.Attendance;
import com.mycompany.studentmanagementsystem.model.Course;
import com.mycompany.studentmanagementsystem.model.Department;
import com.mycompany.studentmanagementsystem.model.FullTimeInstructor;
import com.mycompany.studentmanagementsystem.model.GraduateStudent;
import com.mycompany.studentmanagementsystem.model.Grade;
import com.mycompany.studentmanagementsystem.model.Instructor;
import com.mycompany.studentmanagementsystem.model.PartTimeInstructor;
import com.mycompany.studentmanagementsystem.model.Student;
import com.mycompany.studentmanagementsystem.model.UndergraduateStudent;
import com.mycompany.studentmanagementsystem.enums.AttendanceStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class StudentManagementSystem {

    private final Scanner scanner = new Scanner(System.in);

    private final Department department;
    private final List<Student> students;
    private final List<Instructor> instructors;
    private final List<Course> courses;
    private final List<Grade> grades;
    private final List<Attendance> attendances;
    private final HTMLReportService htmlReportService;

    // Constructor
    public StudentManagementSystem() {
        this.department = new Department("Computer Science");
        this.students = new ArrayList<>();
        this.instructors = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.attendances = new ArrayList<>();
        this.htmlReportService = new HTMLReportService();
    }

    public String readChoice() {
        return scanner.nextLine().trim();
    }

    // ========================= Students =========================

    public void addStudent() {
        System.out.println("\n--- Add Student ---");
        try {
            System.out.print("First Name: ");
            String firstName = scanner.nextLine().trim();

            System.out.print("Last Name: ");
            String lastName = scanner.nextLine().trim();

            int age = readInt("Age: ");

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Phone: ");
            String phone = scanner.nextLine().trim();

            System.out.print("Address: ");
            String address = scanner.nextLine().trim();

            System.out.print("Gender: ");
            String gender = scanner.nextLine().trim();

            System.out.print("Major: ");
            String major = scanner.nextLine().trim();

            int yearLevel = readInt("Year Level: ");

            System.out.println("1. Undergraduate");
            System.out.println("2. Graduate");
            System.out.print("Student Type: ");
            String type = scanner.nextLine().trim();

            Student student;
            if (type.equals("1")) {
                student = new UndergraduateStudent(firstName, lastName, age, email, phone,
                        address, gender, major, yearLevel, 0.0);
            } else if (type.equals("2")) {
                System.out.print("Research Area: ");
                String researchArea = scanner.nextLine().trim();
                student = new GraduateStudent(firstName, lastName, age, email, phone,
                        address, gender, major, yearLevel, 0.0, researchArea);
            } else {
                System.out.println("Invalid student type.");
                return;
            }

            students.add(student);
            department.addStudent(student);
            System.out.println("Student added successfully. ID: " + student.getId());

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void editStudent() {
        System.out.println("\n--- Edit Student ---");
        System.out.print("Enter Student ID: ");
        Student student = findStudentById(scanner.nextLine().trim());

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Editing: " + student.getFullName());
        System.out.println("Leave a field empty and press Enter to keep its current value.\n");

        try {
            System.out.print("First Name [" + student.getFirstName() + "]: ");
            String firstName = scanner.nextLine().trim();
            if (!firstName.isEmpty()) student.setFirstName(firstName);

            System.out.print("Last Name [" + student.getLastName() + "]: ");
            String lastName = scanner.nextLine().trim();
            if (!lastName.isEmpty()) student.setLastName(lastName);

            System.out.print("Age [" + student.getAge() + "]: ");
            String ageInput = scanner.nextLine().trim();
            if (!ageInput.isEmpty()) student.setAge(Integer.parseInt(ageInput));

            System.out.print("Email [" + student.getEmail() + "]: ");
            String email = scanner.nextLine().trim();
            if (!email.isEmpty()) student.setEmail(email);

            System.out.print("Phone [" + student.getPhone() + "]: ");
            String phone = scanner.nextLine().trim();
            if (!phone.isEmpty()) student.setPhone(phone);

            System.out.print("Address [" + student.getAddress() + "]: ");
            String address = scanner.nextLine().trim();
            if (!address.isEmpty()) student.setAddress(address);

            System.out.print("Gender [" + student.getGender() + "]: ");
            String gender = scanner.nextLine().trim();
            if (!gender.isEmpty()) student.setGender(gender);

            System.out.print("Major [" + student.getMajor() + "]: ");
            String major = scanner.nextLine().trim();
            if (!major.isEmpty()) student.setMajor(major);

            System.out.print("Year Level [" + student.getYearLevel() + "]: ");
            String yearInput = scanner.nextLine().trim();
            if (!yearInput.isEmpty()) student.setYearLevel(Integer.parseInt(yearInput));

            System.out.print("GPA [" + student.getGpa() + "]: ");
            String gpaInput = scanner.nextLine().trim();
            if (!gpaInput.isEmpty()) student.setGpa(Double.parseDouble(gpaInput));

            if (student instanceof GraduateStudent gs) {
                System.out.print("Research Area [" + gs.getResearchArea() + "]: ");
                String researchArea = scanner.nextLine().trim();
                if (!researchArea.isEmpty()) gs.setResearchArea(researchArea);
            }

            System.out.println("Student updated successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        System.out.print("Enter Student ID: ");
        Student student = findStudentById(scanner.nextLine().trim());

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        students.remove(student);
        department.removeStudent(student);
        for (Course course : courses) {
            course.removeStudent(student);
        }
        grades.removeIf(g -> g.getStudent().equals(student));
        attendances.removeIf(a -> a.getStudent().equals(student));

        System.out.println("Student deleted successfully.");
    }

    public void listAllStudents() {
        System.out.println("\n--- All Students ---");
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        students.forEach(System.out::println);
    }

    public void searchStudent() {
        System.out.println("\n--- Search Student ---");
        System.out.print("Enter Student ID: ");
        Student student = findStudentById(scanner.nextLine().trim());

        if (student == null) System.out.println("Student not found.");
        else System.out.println(student);
    }

    public void studentFullReport() {
        System.out.println("\n--- Student Full Report ---");
        System.out.print("Enter Student ID: ");
        Student student = findStudentById(scanner.nextLine().trim());

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("\nID: " + student.getId());
        System.out.println("Name: " + student.getFullName());
        System.out.println("Type: " + student.getStudentType());
        System.out.println("Major: " + student.getMajor());
        System.out.println("Year: " + student.getYearLevel());
        System.out.println("GPA: " + String.format("%.2f", student.getGpa()));

        System.out.println("\n--- Grades ---");
        boolean hasGrade = false;
        for (Grade g : grades) {
            if (g.getStudent().equals(student)) {
                hasGrade = true;
                System.out.println(g.getCourse().getCourseName() + " | " + g.getScore() + " | " + g.getLetterGrade());
            }
        }
        if (!hasGrade) System.out.println("No grades found.");

        System.out.println("\n--- Attendance ---");
        boolean hasAtt = false;
        for (Attendance a : attendances) {
            if (a.getStudent().equals(student)) {
                hasAtt = true;
                System.out.println(a.getCourse().getCourseName() + " | " + a.getDate()
                        + " | " + (a.isPresent() ? "Present" : "Absent"));
            }
        }
        if (!hasAtt) System.out.println("No attendance records found.");
    }

    public void topStudents() {
        System.out.println("\n--- Top Students ---");
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        students.stream()
                .sorted(Comparator.comparingDouble(Student::getGpa).reversed())
                .forEach(s -> System.out.println(s.getFullName() + " - GPA: " + String.format("%.2f", s.getGpa())));
    }

    // ========================= Instructors =========================

    public void addInstructor() {
        System.out.println("\n--- Add Instructor ---");
        try {
            System.out.print("First Name: ");
            String firstName = scanner.nextLine().trim();

            System.out.print("Last Name: ");
            String lastName = scanner.nextLine().trim();

            int age = readInt("Age: ");

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Phone: ");
            String phone = scanner.nextLine().trim();

            System.out.print("Address: ");
            String address = scanner.nextLine().trim();

            System.out.print("Gender: ");
            String gender = scanner.nextLine().trim();

            System.out.print("Department: ");
            String dept = scanner.nextLine().trim();

            System.out.print("Specialization: ");
            String spec = scanner.nextLine().trim();

            int exp = readInt("Years of Experience: ");

            System.out.println("1. Full Time");
            System.out.println("2. Part Time");
            System.out.print("Instructor Type: ");
            String type = scanner.nextLine().trim();

            Instructor instructor;
            if (type.equals("1")) {
                double salary = readDouble("Monthly Salary: ");
                instructor = new FullTimeInstructor(firstName, lastName, age, email, phone,
                        address, gender, dept, spec, exp, salary);
            } else if (type.equals("2")) {
                double rate = readDouble("Hourly Rate: ");
                int hours = readInt("Working Hours: ");
                instructor = new PartTimeInstructor(firstName, lastName, age, email, phone,
                        address, gender, dept, spec, exp, rate, hours);
            } else {
                System.out.println("Invalid instructor type.");
                return;
            }

            instructors.add(instructor);
            System.out.println("Instructor added successfully. ID: " + instructor.getId());

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void editInstructor() {
        System.out.println("\n--- Edit Instructor ---");
        System.out.print("Enter Instructor ID: ");
        Instructor instructor = findInstructorById(scanner.nextLine().trim());

        if (instructor == null) {
            System.out.println("Instructor not found.");
            return;
        }

        System.out.println("Editing: " + instructor.getFullName());
        System.out.println("Leave a field empty and press Enter to keep its current value.\n");

        try {
            System.out.print("Department [" + instructor.getDepartment() + "]: ");
            String dept = scanner.nextLine().trim();
            if (!dept.isEmpty()) instructor.setDepartment(dept);

            System.out.print("Specialization [" + instructor.getSpecialization() + "]: ");
            String spec = scanner.nextLine().trim();
            if (!spec.isEmpty()) instructor.setSpecialization(spec);

            System.out.print("Years of Experience [" + instructor.getYearsOfExperience() + "]: ");
            String expInput = scanner.nextLine().trim();
            if (!expInput.isEmpty()) instructor.setYearsOfExperience(Integer.parseInt(expInput));

            if (instructor instanceof FullTimeInstructor ft) {
                System.out.print("Monthly Salary [" + ft.getMonthlySalary() + "]: ");
                String salaryInput = scanner.nextLine().trim();
                if (!salaryInput.isEmpty()) ft.setMonthlySalary(Double.parseDouble(salaryInput));
            } else if (instructor instanceof PartTimeInstructor pt) {
                System.out.print("Hourly Rate [" + pt.getHourlyRate() + "]: ");
                String rateInput = scanner.nextLine().trim();
                if (!rateInput.isEmpty()) pt.setHourlyRate(Double.parseDouble(rateInput));

                System.out.print("Working Hours [" + pt.getWorkingHours() + "]: ");
                String hoursInput = scanner.nextLine().trim();
                if (!hoursInput.isEmpty()) pt.setWorkingHours(Integer.parseInt(hoursInput));
            }

            System.out.println("Instructor updated successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listAllInstructors() {
        System.out.println("\n--- All Instructors ---");
        if (instructors.isEmpty()) System.out.println("No instructors found.");
        else instructors.forEach(System.out::println);
    }

    public void searchInstructor() {
        System.out.println("\n--- Search Instructor ---");
        System.out.print("Enter Instructor ID: ");
        Instructor instructor = findInstructorById(scanner.nextLine().trim());

        if (instructor == null) System.out.println("Instructor not found.");
        else System.out.println(instructor);
    }

    // ========================= Courses =========================

    public void addCourse() {
        System.out.println("\n--- Add Course ---");
        System.out.print("Course ID: ");
        String courseId = scanner.nextLine().trim();

        if (findCourseById(courseId) != null) {
            System.out.println("Course ID already exists.");
            return;
        }

        System.out.print("Course Name: ");
        String courseName = scanner.nextLine().trim();

        System.out.print("Instructor ID: ");
        Instructor instructor = findInstructorById(scanner.nextLine().trim());
        if (instructor == null) {
            System.out.println("Instructor not found.");
            return;
        }

        try {
            Course course = new Course(courseId, courseName, instructor);
            courses.add(course);
            department.addCourse(course);
            System.out.println("Course added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void editCourse() {
        System.out.println("\n--- Edit Course ---");
        System.out.print("Enter Course ID: ");
        Course course = findCourseById(scanner.nextLine().trim());

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        System.out.println("Editing: " + course.getCourseName());
        System.out.println("Leave a field empty and press Enter to keep its current value.\n");

        try {
            boolean updated = false;

            System.out.print("Course Name [" + course.getCourseName() + "]: ");
            String courseName = scanner.nextLine().trim();
            if (!courseName.isEmpty()) {
                if (!courseName.equals(course.getCourseName())) {
                    course.setCourseName(courseName);
                    updated = true;
                }
            }

            System.out.print("Instructor ID [" + course.getInstructor().getId() + "]: ");
            String instructorId = scanner.nextLine().trim();
            if (!instructorId.isEmpty()) {
                Instructor instructor = findInstructorById(instructorId);
                if (instructor == null) {
                    System.out.println("Instructor not found. Instructor was not changed.");
                } else if (!instructor.equals(course.getInstructor())) {
                    course.setInstructor(instructor);
                    updated = true;
                }
            }

            if (updated) {
                System.out.println("Course updated successfully.");
            } else {
                System.out.println("No changes were made.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteCourse() {
        System.out.println("\n--- Delete Course ---");
        System.out.print("Enter Course ID: ");
        Course course = findCourseById(scanner.nextLine().trim());

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        courses.remove(course);
        department.removeCourse(course);
        grades.removeIf(g -> g.getCourse().equals(course));
        attendances.removeIf(a -> a.getCourse().equals(course));

        System.out.println("Course deleted successfully.");
    }

    public void enrollStudentInCourse() {
        System.out.println("\n--- Enroll Student in Course ---");
        System.out.print("Student ID: ");
        Student student = findStudentById(scanner.nextLine().trim());

        System.out.print("Course ID: ");
        Course course = findCourseById(scanner.nextLine().trim());

        if (student == null || course == null) {
            System.out.println("Student or Course not found.");
            return;
        }

        try {
            course.enrollStudent(student);
            System.out.println("Student enrolled successfully.");
        } catch (CourseEnrollmentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeStudentFromCourse() {
        System.out.println("\n--- Remove Student from Course ---");
        System.out.print("Student ID: ");
        Student student = findStudentById(scanner.nextLine().trim());

        System.out.print("Course ID: ");
        Course course = findCourseById(scanner.nextLine().trim());

        if (student == null || course == null) {
            System.out.println("Student or Course not found.");
            return;
        }

        if (course.removeStudent(student)) {
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student is not enrolled in this course.");
        }
    }

    public void listAllCourses() {
        System.out.println("\n--- All Courses ---");
        if (courses.isEmpty()) System.out.println("No courses found.");
        else courses.forEach(System.out::println);
    }

    public void listCourseStudents() {
        System.out.println("\n--- List Course Students ---");
        System.out.print("Course ID: ");
        Course course = findCourseById(scanner.nextLine().trim());

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        List<Student> list = course.getEnrolledStudents();
        if (list.isEmpty()) System.out.println("No students enrolled.");
        else list.forEach(System.out::println);
    }

    public void searchCourse() {
        System.out.println("\n--- Search Course ---");
        System.out.print("Enter Course ID: ");
        Course course = findCourseById(scanner.nextLine().trim());

        if (course == null) System.out.println("Course not found.");
        else System.out.println(course);
    }

    // ========================= Grades =========================

    public void recordGrade() {
        System.out.println("\n--- Record Grade ---");
        System.out.print("Student ID: ");
        Student student = findStudentById(scanner.nextLine().trim());

        System.out.print("Course ID: ");
        Course course = findCourseById(scanner.nextLine().trim());

        if (student == null || course == null) {
            System.out.println("Student or Course not found.");
            return;
        }
        if (!course.isStudentEnrolled(student)) {
            System.out.println("Student is not enrolled in this course.");
            return;
        }
        for (Grade g : grades) {
            if (g.getStudent().equals(student) && g.getCourse().equals(course)) {
                System.out.println("Grade already exists for this student in this course.");
                return;
            }
        }

        double score = readDouble("Score: ");

        try {
            Grade grade = new Grade(student, course, score);
            grades.add(grade);
            updateStudentGPA(student);
            System.out.println("Grade recorded successfully. Letter: " + grade.getLetterGrade());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void calculateStudentGPA() {
        System.out.println("\n--- Calculate Student GPA ---");
        System.out.print("Student ID: ");
        Student student = findStudentById(scanner.nextLine().trim());

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        double gpa = calculateGPA(student);
        student.setGpa(gpa);
        System.out.println("Student GPA: " + String.format("%.2f", gpa));
    }

    public void listAllGrades() {
        System.out.println("\n--- All Grades ---");
        if (grades.isEmpty()) System.out.println("No grades found.");
        else grades.forEach(System.out::println);
    }

    public void failedStudents() {
        System.out.println("\n--- Failed Students in Course ---");
        System.out.print("Course ID: ");
        Course course = findCourseById(scanner.nextLine().trim());

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        boolean found = false;
        for (Grade g : grades) {
            if (g.getCourse().equals(course) && g.getScore() < 60) {
                found = true;
                System.out.println(g.getStudent().getFullName() + " - " + g.getScore());
            }
        }
        if (!found) System.out.println("No failed students found.");
    }

    // ========================= Attendance =========================

    public void recordAttendance() {
        System.out.println("\n--- Record Attendance ---");
        System.out.print("Student ID: ");
        Student student = findStudentById(scanner.nextLine().trim());

        System.out.print("Course ID: ");
        Course course = findCourseById(scanner.nextLine().trim());

        if (student == null || course == null) {
            System.out.println("Student or Course not found.");
            return;
        }
        if (!course.isStudentEnrolled(student)) {
            System.out.println("Student is not enrolled in this course.");
            return;
        }

        System.out.println("1. Present");
        System.out.println("2. Absent");
        System.out.print("Attendance: ");
        String status = scanner.nextLine().trim();

        AttendanceStatus attendanceStatus;
        if (status.equals("1")) {
            attendanceStatus = AttendanceStatus.PRESENT;
        } else if (status.equals("2")) {
            attendanceStatus = AttendanceStatus.ABSENT;
        } else {
            System.out.println("Invalid attendance status.");
            return;
        }
        boolean present = attendanceStatus == AttendanceStatus.PRESENT;

        LocalDate today = LocalDate.now();
        for (Attendance a : attendances) {
            if (a.getStudent().equals(student) && a.getCourse().equals(course) && a.getDate().equals(today)) {
                System.out.println("Attendance already recorded for this student today.");
                return;
            }
        }

        attendances.add(new Attendance(student, course, today, present));
        System.out.println("Attendance recorded successfully.");
    }

    public void listAllAttendances() {
        System.out.println("\n--- All Attendances ---");
        if (attendances.isEmpty()) {
            System.out.println("No attendance records found.");
            return;
        }
        attendances.forEach(System.out::println);
    }

    public void showStudentAttendanceSummary() {
        System.out.println("\n--- Student Attendance Summary ---");
        System.out.print("Student ID: ");
        Student student = findStudentById(scanner.nextLine().trim());

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        int present = 0, absent = 0;
        for (Attendance a : attendances) {
            if (a.getStudent().equals(student)) {
                if (a.isPresent()) present++;
                else absent++;
            }
        }
        int total = present + absent;
        double rate = total == 0 ? 0 : (present * 100.0) / total;

        System.out.println("Present: " + present);
        System.out.println("Absent: " + absent);
        System.out.println("Attendance Rate: " + String.format("%.1f", rate) + "%");
    }

    public void courseAttendanceRate() {
        System.out.println("\n--- Course Attendance Rate ---");
        System.out.print("Course ID: ");
        Course course = findCourseById(scanner.nextLine().trim());

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        int present = 0, total = 0;
        for (Attendance a : attendances) {
            if (a.getCourse().equals(course)) {
                total++;
                if (a.isPresent()) present++;
            }
        }
        double rate = total == 0 ? 0 : (present * 100.0) / total;

        System.out.println("Course: " + course.getCourseName());
        System.out.println("Attendance Rate: " + String.format("%.1f", rate) + "%");
    }

    // ========================= System & Reports =========================

    public void showStatistics() {
        System.out.println("\n--- Statistics ---");
        System.out.println("Students     : " + students.size());
        System.out.println("Instructors  : " + instructors.size());
        System.out.println("Courses      : " + courses.size());
        System.out.println("Grades       : " + grades.size());
        System.out.println("Attendances  : " + attendances.size());
    }

    public void exportStudentsHTML() {
        htmlReportService.exportStudents(students);
    }

    public void exportInstructorsHTML() {
        htmlReportService.exportInstructors(instructors);
    }

    public void exportCoursesHTML() {
        htmlReportService.exportCourses(courses);
    }

    public void exportGradesHTML() {
        htmlReportService.exportGrades(grades);
    }

    public void exportStudentReportHTML() {
        System.out.println("\n--- Export Student Report HTML ---");
        System.out.print("Enter Student ID: ");
        Student student = findStudentById(scanner.nextLine().trim());

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        List<Grade> studentGrades = new ArrayList<>();
        for (Grade g : grades) {
            if (g.getStudent().equals(student)) studentGrades.add(g);
        }

        List<Attendance> studentAttendances = new ArrayList<>();
        for (Attendance a : attendances) {
            if (a.getStudent().equals(student)) studentAttendances.add(a);
        }

        htmlReportService.exportStudentReport(student, studentGrades, studentAttendances);
    }

    public void exportDashboardHTML() {
        htmlReportService.exportDashboard(
                students.size(), instructors.size(), courses.size(), grades.size(), attendances.size());
    }

    // ========================= Helper Methods =========================

    private Student findStudentById(String id) {
        return students.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    private Instructor findInstructorById(String id) {
        return instructors.stream().filter(i -> i.getId().equals(id)).findFirst().orElse(null);
    }

    private Course findCourseById(String id) {
        return courses.stream().filter(c -> c.getCourseId().equals(id)).findFirst().orElse(null);
    }

    private double calculateGPA(Student student) {
        double total = 0;
        int count = 0;
        for (Grade g : grades) {
            if (g.getStudent().equals(student)) {
                total += convertScoreToGPA(g.getScore());
                count++;
            }
        }
        return count == 0 ? 0.0 : total / count;
    }

    private void updateStudentGPA(Student student) {
        student.setGpa(calculateGPA(student));
    }

    private double convertScoreToGPA(double score) {
        if (score >= 90) return 4.0;
        if (score >= 80) return 3.0;
        if (score >= 70) return 2.0;
        if (score >= 60) return 1.0;
        return 0.0;
    }

    // Reads an int safely, re-prompting on invalid input without crashing.
    private int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    // Reads a double safely, re-prompting on invalid input without crashing.
    private double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}