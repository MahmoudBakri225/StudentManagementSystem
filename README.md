# 🎓 Student Management System

> A Java-based Object-Oriented Programming (OOP) console application for managing students, instructors, courses, departments, enrollments, grades, attendance, academic performance, statistics, and HTML reports.

---

## 📌 Overview

The **Student Management System** is a Java Maven console application developed to model and manage common academic operations within a university department.

The system is designed using **Object-Oriented Programming principles** and separates the application into models, services, exceptions, and enumerations.

The application supports different types of students and instructors, course enrollment, grade management, attendance tracking, GPA calculation, validation, statistics, and HTML report generation.

---

## ✨ Key Features

### 👨‍🎓 Student Management

- Add student records
- Edit student information
- Delete student records
- Display student information
- Support different student types
- Undergraduate student management
- Graduate student management
- Store academic information such as major, year level, and GPA

### 👨‍🏫 Instructor Management

- Add instructor records
- Display instructor information
- Assign instructors to courses
- Support different instructor types
- Full-Time instructors
- Part-Time instructors
- Calculate monthly pay for Part-Time instructors

### 📚 Course Management

- Create courses
- Edit course information
- Assign instructors to courses
- Enroll students in courses
- Remove students from courses
- Display course information
- Track enrolled students
- Enforce maximum course capacity

### 📝 Grade Management

- Record grades
- Associate grades with students and courses
- Validate grade scores
- Automatically determine letter grades
- Display recorded grades
- Calculate student GPA
- Track academic performance

### 📅 Attendance Management

- Record student attendance
- Associate attendance records with students and courses
- Store attendance dates
- Track present/absent status
- Display attendance information

### 🏢 Department Management

- Manage departments
- Associate students with departments
- Associate courses with departments
- Maintain department-level academic data

### 📊 Statistics & Reporting

- Generate academic statistics
- Analyze student and course information
- Generate HTML reports
- Present system information in a structured report format

### 🛡️ Validation & Exception Handling

- Validate required information
- Validate numeric values
- Validate grade scores
- Prevent duplicate course enrollment
- Enforce course capacity
- Handle invalid enrollment operations
- Use custom exceptions for enrollment-related errors

---

## 🏗️ System Architecture

The application follows a structured object-oriented architecture.

    User
      │
      ▼
    Main
      │
      ▼
    StudentManagementService
      │
      ├── Student Management
      ├── Instructor Management
      ├── Course Management
      ├── Enrollment Management
      ├── Grade Management
      ├── Attendance Management
      ├── Department Management
      └── Statistics
              │
              ▼
        HTMLReportService
              │
              ▼
          HTML Reports

The **model layer** represents the academic entities, while the **service layer** contains the main business operations.

---

## 🧩 Object-Oriented Design

The project demonstrates the main principles of Object-Oriented Programming.

### 🔒 Encapsulation

Classes keep their data private and expose controlled access through getters and setters.

Core classes include:

    Person
    Student
    Instructor
    Course
    Grade
    Attendance
    Department

This protects object state and allows validation to be applied when data is modified.

---

### 🧬 Inheritance

The project uses inheritance to create specialized versions of common entities.

    Person
    ├── Student
    │   ├── UndergraduateStudent
    │   └── GraduateStudent
    │
    └── Instructor
        ├── FullTimeInstructor
        └── PartTimeInstructor

This allows common properties and behaviors to be defined once in parent classes and reused by specialized classes.

---

### 🔄 Polymorphism

Polymorphism is used through common parent types and overridden methods.

Different student and instructor types can be handled through their common parent classes while providing their own specialized behavior.

Examples:

    Student
    ├── UndergraduateStudent
    └── GraduateStudent

    Instructor
    ├── FullTimeInstructor
    └── PartTimeInstructor

---

### 🧱 Abstraction

Abstract classes are used for common entities that should not be instantiated directly.

The main abstract classes are:

    Person
    Student
    Instructor

Each abstract class defines common behavior for its subclasses.

---

### 🔗 Interface Design

The project includes the `RoleProvider` interface.

    RoleProvider
          ▲
          │
        Person
          │
      ┌───┴────┐
      ▼        ▼
    Student  Instructor

The interface defines the common role behavior:

    public interface RoleProvider {
        String getRole();
    }

`Person` implements the interface while keeping the existing `getRole()` behavior.

This provides a clean abstraction for objects that expose a system role.

---

## 🗺️ UML Class Diagram

The UML diagram represents the actual object-oriented structure of the system, including inheritance, interface implementation, and relationships between the main academic entities.

Place the UML image in:

    docs/uml-class-diagram.png

Then display it in GitHub using:

    ![Student Management System UML Class Diagram](docs/uml-class-diagram.png)

---

## 🔗 Main Class Relationships

The main inheritance and interface relationships are:

    RoleProvider
          ▲
          │ implements
          │
        Person
          │
          ├───────────────┐
          │               │
          ▼               ▼
       Student         Instructor
          │               │
       ┌──┴──┐         ┌──┴────────┐
       ▼     ▼         ▼           ▼
    Undergraduate   FullTime    PartTime
    Student         Instructor  Instructor
    Graduate
    Student

The main academic relationships are:

    Department
       │
       ├── Students
       │
       └── Courses

    Course
       │
       ├── Instructor
       │
       └── Students

    Grade
       │
       ├── Student
       └── Course

    Attendance
       │
       ├── Student
       └── Course

---

## 🧱 Core Domain Classes

### 👤 Person

`Person` is the main abstract base class for people in the system.

It contains common personal information such as:

    id
    firstName
    lastName
    age
    email
    phone
    address
    gender

It also provides common functionality such as:

- ID generation
- Full-name generation
- Getters and setters
- Role definition
- Equality based on ID
- String representation

---

### 👨‍🎓 Student

`Student` is an abstract class extending `Person`.

It contains academic information such as:

    major
    yearLevel
    gpa

It also provides the common student role:

    Student

Specialized student types are:

    UndergraduateStudent
    GraduateStudent

---

### 🎓 UndergraduateStudent

`UndergraduateStudent` represents an undergraduate student.

It specializes the `Student` class and identifies itself using the appropriate student type.

---

### 🎓 GraduateStudent

`GraduateStudent` represents a graduate student.

It extends `Student` and contains an additional research area:

    researchArea

---

### 👨‍🏫 Instructor

`Instructor` is an abstract class extending `Person`.

It contains common instructor information such as:

    department
    specialization
    yearsOfExperience

Specialized instructor types are:

    FullTimeInstructor
    PartTimeInstructor

---

### 👨‍🏫 FullTimeInstructor

`FullTimeInstructor` represents a full-time instructor.

It contains:

    monthlySalary

and identifies itself as a full-time instructor.

---

### 👨‍🏫 PartTimeInstructor

`PartTimeInstructor` represents a part-time instructor.

It contains:

    hourlyRate
    workingHours

and provides functionality for calculating monthly pay.

---

### 📚 Course

`Course` represents an academic course.

Main information includes:

    courseId
    courseName
    instructor
    enrolledStudents

The course also enforces a maximum enrollment capacity.

    MAX_STUDENTS = 50

The course manages its enrolled students and prevents invalid enrollment operations.

---

### 📝 Grade

`Grade` represents the academic result of a student in a specific course.

It maintains a relationship with:

    Student
    Course

The score is validated within the supported range:

    0 - 100

The corresponding letter grade is derived from the score.

---

### 📅 Attendance

`Attendance` represents an attendance record for a student in a course.

It contains information related to:

    Student
    Course
    Date
    Present / Absent status

This allows attendance records to be associated with specific students and courses.

---

### 🏢 Department

`Department` represents a university department.

A department can contain:

    Students
    Courses

This provides a higher-level organization for academic data.

---

## 🔢 Enumerations

The project uses enums to represent predefined categories and statuses.

### `StudentType`

Represents student classifications:

    UNDERGRADUATE
    GRADUATE

### `InstructorType`

Represents instructor classifications:

    FULL_TIME
    PART_TIME

### `AttendanceStatus`

Represents attendance states used by the attendance functionality.

Using enums improves type safety and avoids relying on arbitrary string values.

---

## 🛡️ Course Enrollment Validation

The system contains validation rules for course enrollment.

A student cannot be enrolled if:

    The course is already full

or:

    The student is already enrolled in the course

The maximum number of students per course is:

    50 students

### Enrollment Flow

    Select Student
          │
          ▼
    Select Course
          │
          ▼
    Is Course Full?
       /       \
     Yes        No
      │          │
      ▼          ▼
    Reject    Already Enrolled?
                  /       \
                Yes        No
                 │          │
                 ▼          ▼
               Reject    Enroll Student

Invalid enrollment operations are handled using:

    CourseEnrollmentException

---

## 📝 Grade Management Flow

The grade-management process follows this structure:

    Student
       │
       ▼
    Course Enrollment
       │
       ▼
    Record Grade
       │
       ▼
    Validate Score
       │
       ▼
    Determine Letter Grade
       │
       ▼
    Calculate GPA

A grade connects a student with a specific course:

    Student + Course
           │
           ▼
         Grade

---

## 📊 GPA Calculation

The system provides functionality for calculating student GPA based on recorded academic results.

    Student
      │
      ├── Course
      │     └── Grade
      │
      ├── Course
      │     └── Grade
      │
      └── Course
            └── Grade
                  │
                  ▼
                GPA

This allows the system to track academic performance across multiple courses.

---

## 📅 Attendance Management

Attendance records are associated with both a student and a course.

    Student
       │
       └──────┐
              ▼
          Attendance
              ▲
              │
       Course ─┘

Each attendance record stores the relevant date and attendance status.

---

## 📊 Statistics & Reporting

The system includes functionality for managing and presenting academic information through the service layer.

The project also contains an `HTMLReportService` responsible for generating HTML reports.

    StudentManagementService
              │
              ▼
        Academic Data
              │
              ▼
        HTMLReportService
              │
              ▼
          HTML Report

Keeping report generation in a separate service improves maintainability and separation of responsibilities.

---

## ⚠️ Exception Handling

The project includes a custom exception:

    CourseEnrollmentException

It is used to handle invalid course enrollment operations.

Typical validation cases include:

    Duplicate enrollment
    Course capacity exceeded
    Invalid enrollment operation

This provides clear and controlled handling of enrollment-related errors.

---

## 📦 Collections & Java Features

The project uses Java Collections to manage dynamic academic data.

Collections are used for:

    Students
    Instructors
    Courses
    Grades
    Attendance records

The application also makes use of modern Java features such as:

- `List`
- `ArrayList`
- `Map`
- Streams
- Lambda Expressions
- Method References
- `AtomicInteger`
- Pattern Matching with `instanceof`

These features help keep the implementation organized and maintainable.

---

## 📁 Project Structure

    StudentManagementSystem/
    │
    ├── pom.xml
    ├── README.md
    │
    ├── docs/
    │   └── uml-class-diagram.png
    │
    └── src/
        └── main/
            └── java/
                └── com/
                    └── mycompany/
                        └── studentmanagementsystem/
                            │
                            ├── Main.java
                            │
                            ├── model/
                            │   ├── Person.java
                            │   ├── RoleProvider.java
                            │   ├── Student.java
                            │   ├── UndergraduateStudent.java
                            │   ├── GraduateStudent.java
                            │   ├── Instructor.java
                            │   ├── FullTimeInstructor.java
                            │   ├── PartTimeInstructor.java
                            │   ├── Course.java
                            │   ├── Grade.java
                            │   ├── Attendance.java
                            │   └── Department.java
                            │
                            ├── enums/
                            │   ├── AttendanceStatus.java
                            │   ├── InstructorType.java
                            │   └── StudentType.java
                            │
                            ├── exception/
                            │   └── CourseEnrollmentException.java
                            │
                            └── service/
                                ├── StudentManagementService.java
                                └── HTMLReportService.java

> `InputService.java` is not part of the project structure.

---

## ⚙️ Service Layer

### `StudentManagementService`

The main service responsible for coordinating the application's academic operations.

Its responsibilities include:

    Students
    Instructors
    Courses
    Enrollments
    Grades
    Attendance
    Departments
    Statistics

---

### `HTMLReportService`

Responsible for generating HTML-based reports from the available academic information.

Keeping report generation in a separate service follows the **Single Responsibility Principle** and makes the application easier to maintain.

---

## 🖥️ Console Application

The application provides a menu-driven console interface.

The `Main` class acts as the entry point of the application and interacts with the management service to perform the available operations.

The application covers:

    Student Management
    Instructor Management
    Course Management
    Enrollment
    Grade Management
    Attendance
    Department Management
    Statistics
    HTML Reports

The exact menu options are implemented in `Main.java`.

---

## 🔄 Application Workflow

    Start Application
          │
          ▼
        Main
          │
          ▼
      Display Menu
          │
          ▼
    Select Operation
          │
      ┌───┼────┬──────┬──────┬────────┬─────────┬──────────┐
      ▼   ▼    ▼      ▼      ▼        ▼         ▼          ▼
    Student Instructor Course Enrollment Grade Attendance Department Statistics
      │     │    │      │      │        │         │          │
      └─────┴────┴──────┴──────┴────────┴─────────┴──────────┘
                              │
                              ▼
                        Return to Menu
                              │
                              ▼
                             Exit

---

## 🛠️ Technologies

| Technology | Usage |
|---|---|
| ☕ Java | Application development |
| 📦 Apache Maven | Build and dependency management |
| 🧩 Object-Oriented Programming | System architecture |
| 📋 Java Collections | Data management |
| 🔄 Streams | Data processing |
| λ Lambda Expressions | Functional operations |
| 🔗 Method References | Functional programming support |
| 🛡️ Exception Handling | Error management |
| 🌐 HTML | Report generation |
| 💻 NetBeans IDE | Development environment |

---

## ⚙️ Requirements

To run the project, install:

- Java Development Kit (JDK)
- Apache Maven
- NetBeans IDE or another Java IDE

---

## 🚀 Running the Application

### 💻 Using NetBeans

1. Open **NetBeans IDE**.
2. Open the `StudentManagementSystem` Maven project.
3. Allow Maven to load and configure the project.
4. Build the project.
5. Run `Main.java`.
6. Use the console interface to interact with the system.

### 🧑‍💻 Using Maven

Open a terminal in the project root directory and run:

    mvn clean compile

To build the complete project:

    mvn clean install

The application entry point is:

    com.mycompany.studentmanagementsystem.Main

---

## 🧪 Testing & Verification

The project can be verified through functional execution of the main academic operations.

Important scenarios include:

| Test Area | Expected Behavior |
|---|---|
| Add Student | Student is added successfully |
| Edit Student | Existing student information is updated |
| Delete Student | Student record is removed |
| Add Instructor | Instructor is added successfully |
| Add Course | Course is created successfully |
| Assign Instructor | Instructor is associated with the course |
| Enroll Student | Valid enrollment is accepted |
| Duplicate Enrollment | Enrollment is rejected |
| Course Capacity | Enrollment is rejected when capacity is reached |
| Record Grade | Valid grade is stored |
| Invalid Grade | Invalid score is rejected |
| GPA Calculation | GPA is calculated from recorded grades |
| Attendance | Attendance record is stored correctly |
| HTML Report | Report is generated successfully |

---

## 🎯 Requirements Coverage

| Requirement | Implementation |
|---|:---:|
| Student management | ✅ |
| Undergraduate students | ✅ |
| Graduate students | ✅ |
| Instructor management | ✅ |
| Full-Time instructors | ✅ |
| Part-Time instructors | ✅ |
| Course management | ✅ |
| Course enrollment | ✅ |
| Enrollment validation | ✅ |
| Grade management | ✅ |
| GPA calculation | ✅ |
| Attendance management | ✅ |
| Department management | ✅ |
| Statistics | ✅ |
| HTML report generation | ✅ |
| Encapsulation | ✅ |
| Inheritance | ✅ |
| Polymorphism | ✅ |
| Abstraction | ✅ |
| Interface implementation | ✅ |
| Enums | ✅ |
| Exception handling | ✅ |
| Java Collections | ✅ |
| Maven project | ✅ |
| Console interface | ✅ |

---

## 🧠 Design Highlights

### 🔐 Encapsulated Data

Core data is protected through private attributes and controlled access.

### 👥 Specialized Domain Models

The system supports specialized student and instructor types through inheritance.

### 📚 Course Management

Courses can be created, assigned to instructors, and used for student enrollment.

### 🛡️ Enrollment Validation

The system prevents duplicate enrollment and enforces course capacity.

### 📝 Academic Tracking

Grades and attendance are associated with students and courses.

### 📊 GPA Calculation

The system provides GPA calculation based on recorded academic results.

### 🔗 Interface-Based Roles

`RoleProvider` provides a common contract for role-related behavior.

### 📦 Service Separation

Management operations and HTML report generation are separated into dedicated services.

---

## 📈 Maintainability

The system is structured to allow future extensions without redesigning the complete application.

For example, additional student types can be introduced through the existing inheritance hierarchy:

    Student
       │
       ├── UndergraduateStudent
       ├── GraduateStudent
       └── FutureStudentType

Additional instructor types can follow the same approach.

The service-based structure also allows new functionality to be added through dedicated services.

---

## 🔮 Future Enhancements

The project can be extended in the future with:

- 🔐 User authentication and authorization
- 👥 Additional user roles
- 💾 Database integration
- 🌐 REST API
- 🖥️ Graphical User Interface
- 📱 Web-based interface
- 📊 Advanced dashboards
- 📄 Student transcript generation
- 📚 Course prerequisites
- 🔄 Course withdrawal
- 📅 Semester and academic-year management
- 🧪 Automated unit testing
- 🔬 Integration testing
- ☁️ Cloud deployment

---

## 🎓 Learning Outcomes

This project demonstrates practical experience with:

- Object-Oriented Programming
- Encapsulation
- Inheritance
- Polymorphism
- Abstraction
- Interfaces
- Java Collections
- Streams
- Lambda Expressions
- Method References
- Enumerations
- Exception Handling
- Input Validation
- Service Layer Design
- Object Relationships
- Academic Data Management
- Maven Project Management
- HTML Report Generation

---

## 🎓 Academic Context

**Program:** Digital Egypt Pioneers Initiative (DEPI)

**Organization:** Ministry of Communications and Information Technology (MCIT)

**Track:** Software Development / Java

**Group:** ONL5_SWD6_G1

**Academic Year:** 2026/2027

**Project:** Student Management System

---

## 👨‍💻 Author

### Mahmoud Bakri AbdelSamea Ahmed

**Software Testing Engineer**

### 🔍 Areas of Interest

- Software Testing
- Quality Assurance
- Java
- Object-Oriented Programming
- Test Automation
- Software Quality
- Software Development

### 🔗 GitHub

**MahmoudBakri225**

---

## 📌 Project Status

**Status: Completed**

The project implements the main academic management requirements through a Java Maven console application.

The implemented architecture includes:

    RoleProvider
          ▲
          │
        Person
          │
          ├── Student
          │     ├── UndergraduateStudent
          │     └── GraduateStudent
          │
          └── Instructor
                ├── FullTimeInstructor
                └── PartTimeInstructor

    Department
          │
          ├── Students
          └── Courses

    Course
          │
          ├── Instructor
          └── Students

    Grade
          │
          ├── Student
          └── Course

    Attendance
          │
          ├── Student
          └── Course

The system combines these components through the service layer to provide a complete academic management workflow.

---

## 📄 License

This project was developed for educational and portfolio purposes.

---

<div align="center">

### 🎓 Student Management System

**Java • Maven • OOP • Console Application**



Built with Java and Maven.

</div>
