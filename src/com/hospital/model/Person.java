package com.hospital.model;

/**
 * Feature #17 (Abstract class + abstract method): Person is the abstract
 * base class of the inheritance hierarchy (Feature #14): Person -> Patient, Doctor.
 *
 * Feature #1 (Encapsulation): all fields are private with public getters/setters.
 */
public abstract class Person {

    // ---- Feature #2: instance variables (varied data types) ----
    private String name;
    private int age;
    private char gender;        // 'M', 'F', 'O'
    private String contactNumber;

    // ---- Feature #2: final constant (class-level, shared by all Persons) ----
    public static final String HOSPITAL_NAME = "REVA CITY HOSPITAL";

    // ---- Feature #11: static field (shared across all Person objects) ----
    private static int totalPersonsCreated = 0;

    // unique id assigned using the static counter above
    private final int personId;

    // ---- Feature #9: Constructor overloading (default + parameterized) ----
    public Person() {
        // Feature #12: explicit use of 'this' -> constructor chaining via this()
        this("Unknown", 0, 'O', "N/A");
    }

    public Person(String name, int age, char gender, String contactNumber) {
        totalPersonsCreated++;              // static field updated
        this.personId = totalPersonsCreated; // Feature #12: 'this' resolves shadowing
                                              // (parameter name 'name' shadows the field name below)
        this.name = name == null || name.trim().isEmpty() ? "Unknown" : name.trim();
        this.age = Math.max(age, 0);
        this.gender = gender;
        this.contactNumber = contactNumber == null || contactNumber.trim().isEmpty()
            ? "N/A" : contactNumber.trim();
    }

    // ---- Feature #1: getters / setters (encapsulation) ----
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null || name.trim().isEmpty() ? "Unknown" : name.trim();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        // Feature #6: if-else + jump statement (return) used inside a setter
        if (age < 0) {
            System.out.println("Invalid age ignored for " + this.name);
            return; // jump statement: exits the method early
        }
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber == null || contactNumber.trim().isEmpty()
            ? "N/A" : contactNumber.trim();
    }

    public int getPersonId() {
        return personId;
    }

    // ---- Feature #11: static method ----
    public static int getTotalPersonsCreated() {
        return totalPersonsCreated;
    }

    // ---- Feature #17: abstract method, implemented differently by each subclass ----
    public abstract String displayRole();

    /**
     * Feature #20 (final method): declared final because the age-category
     * business rule is a fixed hospital policy that must NOT be overridden
     * or altered by any subclass (Patient/Doctor age brackets must stay consistent
     * for billing and triage rules across the whole system).
     */
    public final String ageCategory() {
        String category;
        // Feature #6: if-else ladder (control flow)
        if (age < 13) {
            category = "Child";
        } else if (age < 60) {
            category = "Adult";
        } else {
            category = "Senior Citizen";
        }
        return category; // jump statement: return
    }

    // ---- Feature #19: overriding toString() from Object class ----
    @Override
    public String toString() {
        return String.format("[ID=%d] %s | Age: %d | Gender: %c | Contact: %s",
                personId, name, age, gender, contactNumber);
    }

    // ---- Feature #19: overriding equals() from Object class ----
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Person)) return false;
        Person other = (Person) obj;
        return this.personId == other.personId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(personId);
    }
}
