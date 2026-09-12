package com.mycompany.studentmanagementsystem.model;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Person implements RoleProvider {

    private static final AtomicInteger NEXT_ID = new AtomicInteger(1);

    private final String id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String phone;
    private String address;
    private String gender;

    // Constructor
    // Validation runs first (via setters); the ID is only assigned once
    // every field is confirmed valid, so a failed construction never
    // burns an ID number.
    public Person(String firstName, String lastName, int age,
                  String email, String phone, String address, String gender) {
        setFirstName(firstName);
        setLastName(lastName);
        setAge(age);
        setEmail(email);
        setPhone(phone);
        setAddress(address);
        setGender(gender);
        this.id = String.valueOf(NEXT_ID.getAndIncrement());
    }

    // getter
    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    // setter
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty.");
        }
        this.lastName = lastName.trim();
    }

    public void setAge(int age) {
        if (age <= 0) {
            throw new IllegalArgumentException("Age must be greater than zero.");
        }
        this.age = age;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()
                || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email.trim();
    }

    public void setPhone(String phone) {
        if (phone == null || !phone.matches("\\+201\\d{9}")) {
            throw new IllegalArgumentException("Phone must start with +201 and contain 11 digits.");
        }
        this.phone = phone;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty.");
        }
        this.address = address.trim();
    }

    public void setGender(String gender) {
        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be null or empty.");
        }
        this.gender = gender.trim();
    }

    @Override
    public abstract String getRole();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person other = (Person) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getFullName() + " (ID: " + id + ")";
    }
}