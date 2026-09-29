package com.hospital.main;

import java.util.Scanner;

import com.hospital.model.Department;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import com.hospital.model.Payable;
import com.hospital.model.Person;
import com.hospital.service.HospitalService;

public class HospitalApp {

    // ---- Feature #2: static (class-level) variable, shared across the whole run ----
    private static int menuVisits = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HospitalService service = new HospitalService();

        System.out.println("Welcome to " + Person.HOSPITAL_NAME);
        seedSampleData(service);

        int choice;
        // Feature #6: do-while loop driving the menu
        do {
            menuVisits++; // static field mutated
            printMenu();
            choice = readIntSafely(scanner);

            if (choice == Integer.MIN_VALUE) {
                System.out.println("No more input. Exiting.");
                choice = 0;
                break;
            }

            // Feature #6: switch statement with break jump statements
            switch (choice) {
                case 1:
                    registerPatient(scanner, service);
                    break;
                case 2:
                    registerDoctor(scanner, service);
                    break;
                case 3:
                    service.listAllPatients();
                    break;
                case 4:
                    service.listAllDoctors();
                    break;
                case 5:
                    searchPatient(scanner, service);
                    break;
                case 6:
                    showBills(service);
                    break;
                case 7:
                    showDynamicBindingDemo(service);
                    break;
                case 8:
                    showStats(service);
                    break;
                case 0:
                    System.out.println("Exiting. Thank you for using " + Person.HOSPITAL_NAME);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        } while (choice != 0);

        System.out.println("Total menu screens shown this session: " + menuVisits);
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=================================");
        System.out.println("     " + Person.HOSPITAL_NAME + " - MAIN MENU");
        System.out.println("=================================");
        System.out.println("1. Register New Patient");
        System.out.println("2. Register New Doctor");
        System.out.println("3. List All Patients");
        System.out.println("4. List All Doctors");
        System.out.println("5. Search Patient by Name");
        System.out.println("6. Show Bills (Payable interface demo)");
        System.out.println("7. Dynamic Binding Demo (Person references)");
        System.out.println("8. Hospital Statistics");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int readIntSafely(Scanner scanner) {
        // Feature #2: local variable (method scope only, exists only inside this call)
        int value;
        try {
            String line = readLineSafely(scanner);
            if (line == null) {
                return Integer.MIN_VALUE;
            }
            value = Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            value = -1; // sentinel for "invalid"
        }
        return value;
    }

    private static String readLineSafely(Scanner scanner) {
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }

    private static void registerPatient(Scanner scanner, HospitalService service) {
        System.out.print("Name: ");
        String name = readLineSafely(scanner);
        name = name == null ? "Unknown" : name.trim(); // Feature #13: String.trim()

        System.out.print("Age: ");
        int age = readIntSafely(scanner);

        System.out.print("Gender (M/F/O): ");
        String genderInput = readLineSafely(scanner);
        genderInput = genderInput == null ? "" : genderInput.trim();
        char gender = genderInput.isEmpty() ? 'O' : genderInput.charAt(0);

        System.out.print("Contact Number: ");
        String contact = readLineSafely(scanner);
        contact = contact == null ? "N/A" : contact.trim();

        System.out.print("Department (1-Cardiology 2-Neurology 3-Orthopedics 4-Pediatrics 5-General): ");
        int deptChoice = readIntSafely(scanner);
        Department department;
        // Feature #6: another switch example, this time returning into a variable
        switch (deptChoice) {
            case 1: department = Department.CARDIOLOGY; break;
            case 2: department = Department.NEUROLOGY; break;
            case 3: department = Department.ORTHOPEDICS; break;
            case 4: department = Department.PEDIATRICS; break;
            default: department = Department.GENERAL; break;
        }

        System.out.print("Number of days admitted: ");
        int days = readIntSafely(scanner);
        if (days < 0) days = 1;

        System.out.print("Is this an emergency case? (yes/no): ");
        String emergencyInput = readLineSafely(scanner);
        emergencyInput = emergencyInput == null ? "" : emergencyInput.trim();
        boolean emergency = emergencyInput.equalsIgnoreCase("yes"); // String method

        System.out.print("Enter symptoms (comma separated): ");
        String symptomLine = readLineSafely(scanner);

        // Feature #9: parameterized constructor used here
        Patient patient = new Patient(name, age, gender, contact, department, days, 0.0, emergency);
        patient.parseSymptoms(symptomLine); // Feature #13: split/trim/compareTo used inside

        service.addPatient(patient);
        System.out.println("Patient registered successfully with ID: " + patient.getPatientId());
    }

    private static void registerDoctor(Scanner scanner, HospitalService service) {
        System.out.print("Name: ");
        String name = readLineSafely(scanner);
        name = name == null ? "Unknown" : name.trim();
        System.out.print("Age: ");
        int age = readIntSafely(scanner);
        System.out.print("Gender (M/F/O): ");
        String genderInput = readLineSafely(scanner);
        genderInput = genderInput == null ? "" : genderInput.trim();
        char gender = genderInput.isEmpty() ? 'O' : genderInput.charAt(0);
        System.out.print("Contact Number: ");
        String contact = readLineSafely(scanner);
        contact = contact == null ? "N/A" : contact.trim();
        System.out.print("Specialization: ");
        String specialization = readLineSafely(scanner);
        specialization = specialization == null ? "General Physician" : specialization.trim();
        System.out.print("Years of experience: ");
        int experience = readIntSafely(scanner);
        System.out.print("Monthly salary: ");
        double salary = readDoubleSafely(scanner);

        Doctor doctor = new Doctor(name, age, gender, contact, specialization,
                Department.GENERAL, salary, Math.max(experience, 0));
        service.addDoctor(doctor);
        System.out.println("Doctor registered successfully.");
    }

    private static double readDoubleSafely(Scanner scanner) {
        double value;
        try {
            String line = readLineSafely(scanner);
            if (line == null) {
                return 0.0;
            }
            value = Double.parseDouble(line.trim());
        } catch (NumberFormatException e) {
            value = 0.0;
        }
        return value;
    }

    private static void searchPatient(Scanner scanner, HospitalService service) {
        System.out.print("Enter name (or part of name) to search: ");
        String query = readLineSafely(scanner);
        Patient found = service.searchPatientByName(query);
        if (found != null) {
            System.out.println("Found: " + found);
            System.out.println("Age Category (final method): " + found.ageCategory());
        } else {
            System.out.println("No matching patient found.");
        }
    }

    private static void showBills(HospitalService service) {
        System.out.println("\n--- Patient Bills ---");
        Patient[] patients = service.getPatients();
        for (int i = 0; i < service.getPatientCount(); i++) {
            Patient p = patients[i];
            // Feature #18: interface reference
            Payable payable = p;
            System.out.printf("%s (%s) -> %s | Rounded (cast to int): Rs. %d%n",
                    p.getName(), p.getPatientId(), payable.generateReceiptLine(),
                    p.getRoundedBillAmount());
        }
        System.out.printf("TOTAL HOSPITAL REVENUE: Rs. %.2f%n", service.totalHospitalRevenue());
    }

    private static void showDynamicBindingDemo(HospitalService service) {
        // Feature #16: array of Person references pointing to Patient/Doctor objects
        Person[] roster = new Person[service.getPatientCount() + service.getDoctorCount()];
        int idx = 0;
        for (int i = 0; i < service.getPatientCount(); i++) {
            roster[idx++] = service.getPatients()[i]; // Patient object, Person reference
        }
        for (int i = 0; i < service.getDoctorCount(); i++) {
            roster[idx++] = service.getDoctors()[i]; // Doctor object, Person reference
        }
        service.printRoster(roster);
    }

    private static void showStats(HospitalService service) {
        System.out.println("\n--- Hospital Statistics (static fields/methods) ---");
        System.out.println("Total Persons created ever: " + Person.getTotalPersonsCreated());
        System.out.println("Total Patients created ever: " + Patient.getTotalPatients());
        System.out.println("Total Doctors created ever: " + Doctor.getTotalDoctors());
        System.out.println("Menu screens shown so far: " + menuVisits);
    }

    private static void seedSampleData(HospitalService service) {
        service.addPatient(new Patient("Aarav Mehta", 34, 'M', "9876543210",
                Department.CARDIOLOGY, 3, 500.0, false));
        service.addPatient(new Patient("Sneha Rao", 8, 'F', "9876500000",
                Department.PEDIATRICS, 2, 0.0, true));
        service.addDoctor(new Doctor("Dr. Kavita Iyer", 45, 'F', "9123456780",
                "Cardiologist", Department.CARDIOLOGY, 120000.0, 15));
        service.addDoctor(new Doctor("Dr. Rohan Das", 29, 'M', "9123456781",
                "Pediatrician", Department.PEDIATRICS, 70000.0, 3));
    }
}
