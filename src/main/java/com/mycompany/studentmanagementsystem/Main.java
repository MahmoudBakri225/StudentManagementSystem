package com.mycompany.studentmanagementsystem;

import com.mycompany.studentmanagementsystem.service.StudentManagementSystem;

public class Main {

    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();

        while (true) {
            System.out.println("\n========================================");
            System.out.println("       Student Management System");
            System.out.println("========================================");

            System.out.println("--- Students ---");
            System.out.println("1.  Add Student");
            System.out.println("2.  Edit Student");
            System.out.println("3.  Delete Student");
            System.out.println("4.  List All Students");
            System.out.println("5.  Search Student");
            System.out.println("6.  Student Full Report");
            System.out.println("7.  Top Students (GPA)");

            System.out.println("--- Instructors ---");
            System.out.println("8.  Add Instructor");
            System.out.println("9.  Edit Instructor");
            System.out.println("10. List All Instructors");
            System.out.println("11. Search Instructor");

            System.out.println("--- Courses ---");
            System.out.println("12. Add Course");
            System.out.println("13. Edit Course");
            System.out.println("14. Delete Course");
            System.out.println("15. Enroll Student in Course");
            System.out.println("16. Remove Student from Course");
            System.out.println("17. List All Courses");
            System.out.println("18. List Course Students");
            System.out.println("19. Search Course");

            System.out.println("--- Grades ---");
            System.out.println("20. Record Grade");
            System.out.println("21. Calculate Student GPA");
            System.out.println("22. List All Grades");
            System.out.println("23. Failed Students in Course");

            System.out.println("--- Attendance ---");
            System.out.println("24. Record Attendance");
            System.out.println("25. List All Attendances");
            System.out.println("26. Student Attendance Summary");
            System.out.println("27. Course Attendance Rate");

            System.out.println("--- System ---");
            System.out.println("28. Statistics");

            System.out.println("--- Export HTML ---");
            System.out.println("29. Export Students HTML");
            System.out.println("30. Export Instructors HTML");
            System.out.println("31. Export Courses HTML");
            System.out.println("32. Export Grades HTML");
            System.out.println("33. Export Student Report HTML");
            System.out.println("34. Export Dashboard HTML");

            System.out.println("0.  Exit");
            System.out.print("\nEnter your choice: ");

            String choice = system.readChoice();

            switch (choice) {
                case "1"  -> system.addStudent();
                case "2"  -> system.editStudent();
                case "3"  -> system.deleteStudent();
                case "4"  -> system.listAllStudents();
                case "5"  -> system.searchStudent();
                case "6"  -> system.studentFullReport();
                case "7"  -> system.topStudents();
                case "8"  -> system.addInstructor();
                case "9"  -> system.editInstructor();
                case "10" -> system.listAllInstructors();
                case "11" -> system.searchInstructor();
                case "12" -> system.addCourse();
                case "13" -> system.editCourse();
                case "14" -> system.deleteCourse();
                case "15" -> system.enrollStudentInCourse();
                case "16" -> system.removeStudentFromCourse();
                case "17" -> system.listAllCourses();
                case "18" -> system.listCourseStudents();
                case "19" -> system.searchCourse();
                case "20" -> system.recordGrade();
                case "21" -> system.calculateStudentGPA();
                case "22" -> system.listAllGrades();
                case "23" -> system.failedStudents();
                case "24" -> system.recordAttendance();
                case "25" -> system.listAllAttendances();
                case "26" -> system.showStudentAttendanceSummary();
                case "27" -> system.courseAttendanceRate();
                case "28" -> system.showStatistics();
                case "29" -> system.exportStudentsHTML();
                case "30" -> system.exportInstructorsHTML();
                case "31" -> system.exportCoursesHTML();
                case "32" -> system.exportGradesHTML();
                case "33" -> system.exportStudentReportHTML();
                case "34" -> system.exportDashboardHTML();
                case "0"  -> {
                    System.out.println("\nThank you for using the system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
