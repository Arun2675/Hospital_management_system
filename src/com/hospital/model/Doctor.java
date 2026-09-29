package com.hospital.model;

/**
 * Feature #14 (Inheritance hierarchy): second subclass of Person,
 * forming Person -> Patient, Doctor.
 */
public class Doctor extends Person {

    // ---- Feature #1: private fields ----
    private String specialization;
    private Department department;
    private double monthlySalary;
    private int yearsOfExperience;

    // ---- Feature #11: static field ----
    private static int totalDoctors = 0;

    // ---- Feature #9: constructor overloading ----
    public Doctor() {
        super(); // Feature #15: super() call to parent no-arg constructor
        this.specialization = "General Physician";
        this.department = Department.GENERAL;
        this.monthlySalary = 50000.0;
        this.yearsOfExperience = 0;
        totalDoctors++;
    }

    public Doctor(String name, int age, char gender, String contactNumber,
                  String specialization, Department department,
                  double monthlySalary, int yearsOfExperience) {
        super(name, age, gender, contactNumber); // Feature #15: super(...) parameterized
        this.specialization = specialization;
        this.department = department == null ? Department.GENERAL : department;
        this.monthlySalary = Math.max(monthlySalary, 0.0);
        this.yearsOfExperience = Math.max(yearsOfExperience, 0);
        totalDoctors++;
    }

    // ---- getters / setters (encapsulation) ----
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department == null ? Department.GENERAL : department;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = Math.max(yearsOfExperience, 0);
    }

    public static int getTotalDoctors() {
        return totalDoctors;
    }

    // ---- Feature #10: method overloading ----
    // Version 1: flat bonus amount
    public void addBonus(double flatAmount) {
        this.monthlySalary += flatAmount;
    }

    // Version 2 (overloaded, different signature): percentage-based bonus
    public void addBonus(double percentage, boolean isPercentage) {
        // Feature #6: if-else
        if (isPercentage) {
            this.monthlySalary += this.monthlySalary * (percentage / 100.0);
        } else {
            this.monthlySalary += percentage;
        }
    }

    /**
     * Grade the doctor's seniority using a switch statement on an int
     * (Feature #6: switch + break jump statements).
     */
    public String seniorityLevel() {
        String level;
        int bracket = yearsOfExperience / 5; // integer division
        switch (bracket) {
            case 0:
                level = "Junior";
                break;
            case 1:
                level = "Mid-Level";
                break;
            case 2:
                level = "Senior";
                break;
            default:
                level = "Chief Consultant";
                break;
        }
        return level;
    }

    // ---- Feature #16: overriding with dynamic binding ----
    @Override
    public String displayRole() {
        return "Doctor";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
                " | Specialization: %s | Dept: %s | Experience: %d yrs | Level: %s",
                specialization, department, yearsOfExperience, seniorityLevel());
    }
}
