
package com.mycompany.studentmanagementsystem.service;

import com.mycompany.studentmanagementsystem.model.Attendance;
import com.mycompany.studentmanagementsystem.model.Course;
import com.mycompany.studentmanagementsystem.model.FullTimeInstructor;
import com.mycompany.studentmanagementsystem.model.Grade;
import com.mycompany.studentmanagementsystem.model.Instructor;
import com.mycompany.studentmanagementsystem.model.PartTimeInstructor;
import com.mycompany.studentmanagementsystem.model.Student;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HTMLReportService {

    private static final String REPORT_FOLDER = "reports";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM d, yyyy");

    // =========================
    // Common HTML Template (Academic Ledger design)
    // =========================
    private StringBuilder createHTMLPage(String pageTitle, String reportTitle) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html lang='en'><head>");
        html.append("<meta charset='UTF-8'>");
        html.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        html.append("<title>").append(escapeHTML(pageTitle)).append("</title>");
        html.append("<link rel='preconnect' href='https://fonts.googleapis.com'>");
        html.append("<link href='https://fonts.googleapis.com/css2?family=Source+Serif+4:wght@500;600;700&family=IBM+Plex+Sans:wght@400;500;600&display=swap' rel='stylesheet'>");
        html.append("<style>");
        html.append("""
            :root {
                --paper: #F6F3EC;
                --paper-raised: #FCFAF5;
                --ink: #23291F;
                --ink-soft: #6B6A5C;
                --rule: #D8D2C0;
                --emerald: #1F6F52;
                --brass: #A9822F;
                --rust: #A33B2B;
                --amber: #A8752E;
            }
            * { margin: 0; padding: 0; box-sizing: border-box; }
            body {
                font-family: 'IBM Plex Sans', 'Segoe UI', sans-serif;
                background: var(--paper);
                color: var(--ink);
                line-height: 1.6;
            }
            .letterhead {
                display: flex;
                align-items: center;
                gap: 20px;
                padding: 32px 56px 22px;
                border-bottom: 3px double var(--ink);
            }
            .seal {
                flex-shrink: 0;
                width: 54px; height: 54px;
                border-radius: 50%;
                border: 2px solid var(--brass);
                display: flex; align-items: center; justify-content: center;
                font-family: 'Source Serif 4', Georgia, serif;
                font-weight: 700;
                font-size: 1rem;
                color: var(--brass);
                letter-spacing: -0.5px;
            }
            .letterhead-text h1 {
                font-family: 'Source Serif 4', Georgia, serif;
                font-size: 1.7rem;
                font-weight: 600;
                color: var(--ink);
            }
            .letterhead-text p {
                font-size: 0.82rem;
                color: var(--ink-soft);
                margin-top: 2px;
            }
            .title-bar {
                display: flex;
                justify-content: space-between;
                align-items: baseline;
                flex-wrap: wrap;
                gap: 8px;
                padding: 26px 56px 12px;
                border-bottom: 1px solid var(--rule);
            }
            .title-bar h2 {
                font-family: 'Source Serif 4', Georgia, serif;
                font-size: 1.35rem;
                font-weight: 600;
                color: var(--ink);
            }
            .title-bar .date {
                font-size: 0.8rem;
                color: var(--ink-soft);
            }
            .content {
                padding: 12px 56px 60px;
                max-width: 1150px;
                margin: 0 auto;
            }
            h3 {
                font-family: 'Source Serif 4', Georgia, serif;
                font-size: 1.1rem;
                font-weight: 600;
                color: var(--ink);
                margin: 34px 0 14px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                background: var(--paper-raised);
                margin-bottom: 8px;
            }
            th {
                text-align: left;
                padding: 10px 16px;
                font-weight: 600;
                font-size: 0.8rem;
                color: var(--ink-soft);
                border-bottom: 2px solid var(--ink);
            }
            td {
                padding: 12px 16px;
                font-size: 0.92rem;
                border-bottom: 1px solid var(--rule);
            }
            tr:last-child td { border-bottom: 1px solid var(--ink); }
            tbody tr:nth-child(even) td { background: rgba(31, 111, 82, 0.035); }
            .name { font-weight: 600; }
            .gpa, .score {
                font-family: 'Source Serif 4', Georgia, serif;
                font-weight: 600;
                color: var(--emerald);
            }
            .course-id { font-weight: 600; color: var(--brass); }
            .tag {
                display: inline-block;
                padding: 2px 9px;
                border: 1px solid currentColor;
                border-radius: 3px;
                font-size: 0.78rem;
                font-weight: 600;
            }
            .grade-a, .grade-b { color: var(--emerald); }
            .grade-c { color: var(--amber); }
            .grade-d, .grade-f { color: var(--rust); }
            .status {
                display: inline-flex;
                align-items: center;
                gap: 7px;
                font-size: 0.88rem;
                font-weight: 500;
            }
            .status::before {
                content: '';
                width: 7px; height: 7px;
                border-radius: 50%;
                background: currentColor;
            }
            .present { color: var(--emerald); }
            .absent { color: var(--rust); }
            .empty {
                border: 1px dashed var(--rule);
                background: var(--paper-raised);
                padding: 36px;
                text-align: center;
                color: var(--ink-soft);
                font-style: italic;
                margin-bottom: 8px;
            }
            .record-card {
                border: 1px solid var(--rule);
                background: var(--paper-raised);
                padding: 6px 28px;
                margin: 20px 0 8px;
            }
            .record-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            }
            .record-field {
                padding: 18px 20px 18px 0;
                border-bottom: 1px solid var(--rule);
            }
            .record-label {
                font-size: 0.72rem;
                color: var(--ink-soft);
                margin-bottom: 5px;
            }
            .record-value {
                font-family: 'Source Serif 4', Georgia, serif;
                font-size: 1.1rem;
                font-weight: 600;
                color: var(--ink);
            }
            .highlight-card {
                display: flex;
                justify-content: space-between;
                align-items: center;
                gap: 20px;
                flex-wrap: wrap;
                padding: 22px 28px;
                margin: 20px 0 10px;
                background: var(--paper-raised);
                border: 1px solid var(--rule);
            }
            .highlight-item {
                min-width: 150px;
            }
            .highlight-label {
                font-size: 0.75rem;
                color: var(--ink-soft);
                margin-bottom: 4px;
            }
            .highlight-value {
                font-family: 'Source Serif 4', Georgia, serif;
                font-size: 1.55rem;
                font-weight: 600;
                color: var(--emerald);
            }
            .progress {
                width: 100%;
                height: 7px;
                background: var(--rule);
                margin-top: 8px;
                overflow: hidden;
            }
            .progress-fill {
                height: 100%;
                background: var(--emerald);
            }
            .summary-strip {
                display: flex;
                flex-wrap: wrap;
                border-top: 1px solid var(--ink);
                border-bottom: 1px solid var(--ink);
                margin: 24px 0 10px;
            }
            .summary-item {
                flex: 1;
                min-width: 150px;
                padding: 26px 22px;
                border-right: 1px solid var(--rule);
            }
            .summary-item:last-child { border-right: none; }
            .summary-label {
                font-size: 0.8rem;
                color: var(--ink-soft);
                margin-bottom: 10px;
            }
            .summary-count {
                font-family: 'Source Serif 4', Georgia, serif;
                font-size: 2.3rem;
                font-weight: 600;
                color: var(--ink);
            }
            .summary-bar {
                width: 26px; height: 3px;
                background: var(--brass);
                margin-top: 10px;
            }
            .report-footer {
                margin-top: 40px;
                padding-top: 16px;
                border-top: 1px solid var(--rule);
                text-align: center;
                font-family: 'Source Serif 4', Georgia, serif;
                font-style: italic;
                font-size: 0.8rem;
                color: var(--ink-soft);
            }
            @media (max-width: 700px) {
                .letterhead, .title-bar, .content {
                    padding-left: 24px;
                    padding-right: 24px;
                }
                .letterhead-text h1 { font-size: 1.4rem; }
                table { display: block; overflow-x: auto; }
                .summary-item {
                    border-right: none;
                    border-bottom: 1px solid var(--rule);
                }
                .summary-item:last-child { border-bottom: none; }
            }
        """);
        html.append("</style></head><body>");

        html.append("<div class='letterhead'>");
        html.append("<div class='seal'>SMS</div>");
        html.append("<div class='letterhead-text'>");
        html.append("<h1>Student Management System</h1>");
        html.append("<p>Academic Records</p>");
        html.append("</div></div>");

        html.append("<div class='title-bar'>");
        html.append("<h2>").append(escapeHTML(reportTitle)).append("</h2>");
        html.append("<span class='date'>").append(LocalDate.now().format(DATE_FORMAT)).append("</span>");
        html.append("</div>");

        html.append("<div class='content'>");
        return html;
    }

    private void closeHTMLPage(StringBuilder html) {
        html.append("<div class='report-footer'>Issued by the Student Management System</div>");
        html.append("</div></body></html>");
    }

    // =========================
    // Export Methods
    // =========================
    public void exportStudents(List<Student> students) {
        StringBuilder html = createHTMLPage("Students Report", "Students Report");

        if (students.isEmpty()) {
            html.append("<div class='empty'>No students found.</div>");
        } else {
            html.append("<table><thead><tr>");
            html.append("<th>ID</th><th>Name</th><th>Type</th><th>Major</th><th>Year</th><th>GPA</th>");
            html.append("</tr></thead><tbody>");

            for (Student s : students) {
                html.append("<tr>")
                        .append("<td>").append(escapeHTML(s.getId())).append("</td>")
                        .append("<td class='name'>").append(escapeHTML(s.getFullName())).append("</td>")
                        .append("<td>").append(escapeHTML(s.getStudentType().toString())).append("</td>")
                        .append("<td>").append(escapeHTML(s.getMajor())).append("</td>")
                        .append("<td>").append(s.getYearLevel()).append("</td>")
                        .append("<td class='gpa'>").append(String.format("%.2f", s.getGpa())).append("</td>")
                        .append("</tr>");
            }

            html.append("</tbody></table>");
        }

        closeHTMLPage(html);
        saveHTMLFile("students.html", html.toString());
    }

    public void exportInstructors(List<Instructor> instructors) {
        StringBuilder html = createHTMLPage("Instructors Report", "Instructors Report");

        if (instructors.isEmpty()) {
            html.append("<div class='empty'>No instructors found.</div>");
        } else {
            html.append("<table><thead><tr>");
            html.append("<th>ID</th><th>Name</th><th>Type</th><th>Department</th>");
            html.append("<th>Specialization</th><th>Experience</th><th>Compensation</th>");
            html.append("</tr></thead><tbody>");

            for (Instructor i : instructors) {
                html.append("<tr>")
                        .append("<td>").append(escapeHTML(i.getId())).append("</td>")
                        .append("<td class='name'>").append(escapeHTML(i.getFullName())).append("</td>")
                        .append("<td>").append(escapeHTML(i.getInstructorType().toString())).append("</td>")
                        .append("<td>").append(escapeHTML(i.getDepartment())).append("</td>")
                        .append("<td>").append(escapeHTML(i.getSpecialization())).append("</td>")
                        .append("<td>").append(i.getYearsOfExperience()).append(" years</td>");

                if (i instanceof FullTimeInstructor fullTimeInstructor) {
                    html.append("<td>")
                            .append(String.format("%.2f", fullTimeInstructor.getMonthlySalary()))
                            .append(" / month</td>");
                } else if (i instanceof PartTimeInstructor partTimeInstructor) {
                    html.append("<td>")
                            .append(String.format("%.2f", partTimeInstructor.getHourlyRate()))
                            .append(" / hr")
                            .append("</td>");
                } else {
                    html.append("<td>-</td>");
                }

                html.append("</tr>");
            }

            html.append("</tbody></table>");
        }

        closeHTMLPage(html);
        saveHTMLFile("instructors.html", html.toString());
    }

    public void exportCourses(List<Course> courses) {
        StringBuilder html = createHTMLPage("Courses Report", "Courses Report");

        if (courses.isEmpty()) {
            html.append("<div class='empty'>No courses found.</div>");
        } else {
            html.append("<table><thead><tr>");
            html.append("<th>Course ID</th><th>Course Name</th><th>Instructor</th>");
            html.append("<th>Enrollment</th><th>Capacity</th><th>Rate</th>");
            html.append("</tr></thead><tbody>");

            for (Course c : courses) {
                int current = c.getCurrentEnrollment();
                int max = c.getMaxStudents();
                double rate = max == 0 ? 0.0 : (current * 100.0) / max;

                html.append("<tr>")
                        .append("<td class='course-id'>").append(escapeHTML(c.getCourseId())).append("</td>")
                        .append("<td class='name'>").append(escapeHTML(c.getCourseName())).append("</td>")
                        .append("<td>").append(escapeHTML(c.getInstructor().getFullName())).append("</td>")
                        .append("<td>").append(current).append(" / ").append(max).append("</td>")
                        .append("<td>").append(max).append("</td>")
                        .append("<td>")
                        .append(String.format("%.1f%%", rate))
                        .append("</td>")
                        .append("</tr>");
            }

            html.append("</tbody></table>");
        }

        closeHTMLPage(html);
        saveHTMLFile("courses.html", html.toString());
    }

    public void exportGrades(List<Grade> grades) {
        StringBuilder html = createHTMLPage("Grades Report", "Grades Report");

        if (grades.isEmpty()) {
            html.append("<div class='empty'>No grades found.</div>");
        } else {
            html.append("<table><thead><tr>");
            html.append("<th>Student</th><th>Course</th><th>Score</th><th>Letter Grade</th>");
            html.append("</tr></thead><tbody>");

            for (Grade g : grades) {
                html.append("<tr>")
                        .append("<td class='name'>").append(escapeHTML(g.getStudent().getFullName())).append("</td>")
                        .append("<td>").append(escapeHTML(g.getCourse().getCourseName())).append("</td>")
                        .append("<td class='score'>").append(String.format("%.1f", g.getScore())).append("</td>")
                        .append("<td>").append(getGradeTag(g.getLetterGrade())).append("</td>")
                        .append("</tr>");
            }

            html.append("</tbody></table>");
        }

        closeHTMLPage(html);
        saveHTMLFile("grades.html", html.toString());
    }

    public void exportStudentReport(Student student, List<Grade> grades, List<Attendance> attendances) {
        StringBuilder html = createHTMLPage(
                "Student Report",
                "Student Record — " + student.getFullName()
        );

        int presentCount = 0;
        int absentCount = 0;

        for (Attendance a : attendances) {
            if (a.isPresent()) {
                presentCount++;
            } else {
                absentCount++;
            }
        }

        int attendanceCount = presentCount + absentCount;
        double attendanceRate = attendanceCount == 0
                ? 0.0
                : (presentCount * 100.0) / attendanceCount;

        html.append("<div class='record-card'><div class='record-grid'>");

        addRecordField(html, "Student ID", student.getId());
        addRecordField(html, "Full Name", student.getFullName());
        addRecordField(html, "Type", student.getStudentType().toString());
        addRecordField(html, "Major", student.getMajor());
        addRecordField(html, "Year Level", String.valueOf(student.getYearLevel()));
        addRecordField(html, "GPA", String.format("%.2f", student.getGpa()));
        addRecordField(html, "Email", student.getEmail());
        addRecordField(html, "Phone", student.getPhone());
        addRecordField(html, "Address", student.getAddress());
        addRecordField(html, "Gender", student.getGender());

        if (student instanceof com.mycompany.studentmanagementsystem.model.GraduateStudent graduateStudent) {
            addRecordField(html, "Research Area", graduateStudent.getResearchArea());
        }

        html.append("</div></div>");

        html.append("<div class='highlight-card'>");

        html.append("<div class='highlight-item'>");
        html.append("<div class='highlight-label'>Current GPA</div>");
        html.append("<div class='highlight-value'>")
                .append(String.format("%.2f", student.getGpa()))
                .append("</div>");
        html.append("</div>");

        html.append("<div class='highlight-item'>");
        html.append("<div class='highlight-label'>Grades Recorded</div>");
        html.append("<div class='highlight-value'>")
                .append(grades.size())
                .append("</div>");
        html.append("</div>");

        html.append("<div class='highlight-item'>");
        html.append("<div class='highlight-label'>Attendance Rate</div>");
        html.append("<div class='highlight-value'>")
                .append(String.format("%.1f%%", attendanceRate))
                .append("</div>");
        html.append("<div class='progress'>");
        html.append("<div class='progress-fill' style='width:")
                .append(String.format("%.1f", attendanceRate))
                .append("%'></div>");
        html.append("</div>");
        html.append("</div>");

        html.append("</div>");

        html.append("<h3>Grades</h3>");

        if (grades.isEmpty()) {
            html.append("<div class='empty'>No grades found.</div>");
        } else {
            html.append("<table><thead><tr>");
            html.append("<th>Course</th><th>Score</th><th>Letter</th>");
            html.append("</tr></thead><tbody>");

            for (Grade g : grades) {
                html.append("<tr>")
                        .append("<td>").append(escapeHTML(g.getCourse().getCourseName())).append("</td>")
                        .append("<td class='score'>").append(String.format("%.1f", g.getScore())).append("</td>")
                        .append("<td>").append(getGradeTag(g.getLetterGrade())).append("</td>")
                        .append("</tr>");
            }

            html.append("</tbody></table>");
        }

        html.append("<h3>Attendance</h3>");

        if (attendances.isEmpty()) {
            html.append("<div class='empty'>No attendance records found.</div>");
        } else {
            html.append("<table><thead><tr>");
            html.append("<th>Course</th><th>Date</th><th>Status</th>");
            html.append("</tr></thead><tbody>");

            for (Attendance a : attendances) {
                html.append("<tr>")
                        .append("<td>").append(escapeHTML(a.getCourse().getCourseName())).append("</td>")
                        .append("<td>").append(a.getDate().format(DATE_FORMAT)).append("</td>")
                        .append("<td>").append(getAttendanceStatus(a.isPresent())).append("</td>")
                        .append("</tr>");
            }

            html.append("</tbody></table>");

            html.append("<div class='highlight-card'>");

            html.append("<div class='highlight-item'>");
            html.append("<div class='highlight-label'>Present</div>");
            html.append("<div class='highlight-value'>")
                    .append(presentCount)
                    .append("</div>");
            html.append("</div>");

            html.append("<div class='highlight-item'>");
            html.append("<div class='highlight-label'>Absent</div>");
            html.append("<div class='highlight-value'>")
                    .append(absentCount)
                    .append("</div>");
            html.append("</div>");

            html.append("<div class='highlight-item'>");
            html.append("<div class='highlight-label'>Attendance Rate</div>");
            html.append("<div class='highlight-value'>")
                    .append(String.format("%.1f%%", attendanceRate))
                    .append("</div>");
            html.append("</div>");

            html.append("</div>");
        }

        closeHTMLPage(html);
        saveHTMLFile(
                "student_" + escapeFileName(student.getId()) + "_report.html",
                html.toString()
        );
    }

    public void exportDashboard(int studentCount, int instructorCount, int courseCount,
                                int gradeCount, int attendanceCount) {
        StringBuilder html = createHTMLPage("Dashboard", "System Overview");

        html.append("<div class='summary-strip'>");

        addSummaryItem(html, "Students", studentCount);
        addSummaryItem(html, "Instructors", instructorCount);
        addSummaryItem(html, "Courses", courseCount);
        addSummaryItem(html, "Grades", gradeCount);
        addSummaryItem(html, "Attendance Records", attendanceCount);

        html.append("</div>");

        html.append("<h3>System Statistics</h3>");

        html.append("<table><thead><tr>");
        html.append("<th>Category</th><th>Total Records</th>");
        html.append("</tr></thead><tbody>");

        addStatisticsRow(html, "Students", studentCount);
        addStatisticsRow(html, "Instructors", instructorCount);
        addStatisticsRow(html, "Courses", courseCount);
        addStatisticsRow(html, "Grades", gradeCount);
        addStatisticsRow(html, "Attendance Records", attendanceCount);

        html.append("</tbody></table>");

        closeHTMLPage(html);
        saveHTMLFile("dashboard.html", html.toString());
    }

    // =========================
    // Helpers
    // =========================
    private void addRecordField(StringBuilder html, String label, String value) {
        html.append("<div class='record-field'>")
                .append("<div class='record-label'>").append(escapeHTML(label)).append("</div>")
                .append("<div class='record-value'>").append(escapeHTML(value)).append("</div>")
                .append("</div>");
    }

    private void addSummaryItem(StringBuilder html, String label, int count) {
        html.append("<div class='summary-item'>")
                .append("<div class='summary-label'>").append(escapeHTML(label)).append("</div>")
                .append("<div class='summary-count'>").append(count).append("</div>")
                .append("<div class='summary-bar'></div>")
                .append("</div>");
    }

    private void addStatisticsRow(StringBuilder html, String label, int count) {
        html.append("<tr>")
                .append("<td class='name'>").append(escapeHTML(label)).append("</td>")
                .append("<td>").append(count).append("</td>")
                .append("</tr>");
    }

    private String getGradeTag(String grade) {
        String cls = switch (grade) {
            case "A" -> "grade-a";
            case "B" -> "grade-b";
            case "C" -> "grade-c";
            case "D" -> "grade-d";
            default -> "grade-f";
        };

        return "<span class='tag " + cls + "'>"
                + escapeHTML(grade)
                + "</span>";
    }

    private String getAttendanceStatus(boolean present) {
        return present
                ? "<span class='status present'>Present</span>"
                : "<span class='status absent'>Absent</span>";
    }

    private String escapeHTML(String value) {
        if (value == null) {
            return "";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private String escapeFileName(String value) {
        if (value == null || value.isEmpty()) {
            return "unknown";
        }

        return value.replaceAll("[^a-zA-Z0-9_-]", "_");
    }

    private void saveHTMLFile(String fileName, String html) {
        File folder = new File(REPORT_FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, fileName);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(html);

            System.out.println("HTML report generated successfully.");
            System.out.println("File: " + file.getPath());

        } catch (IOException e) {
            System.out.println("Error while generating HTML report: " + e.getMessage());
        }
    }
}
