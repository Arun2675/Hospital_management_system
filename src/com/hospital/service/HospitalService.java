package com.hospital.service;

import java.util.Locale;

import com.hospital.model.Department;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import com.hospital.model.Payable;
import com.hospital.model.Person;

/**
 * Service layer for the Hospital Management System.
 * Feature #21: this class lives in a SECOND package (com.hospital.service),
 * separate from com.hospital.model, and imports model classes explicitly.
 */
public class HospitalService {

    // ---- Feature #7: array of objects ----
    private Patient[] patients;
    private int patientCount;

    private Doctor[] doctors;
    private int doctorCount;

    private static final int MAX_RECORDS = 50; // final constant: local scope demo (Feature #2)

    public HospitalService() {
        patients = new Patient[MAX_RECORDS];
        doctors = new Doctor[MAX_RECORDS];
        patientCount = 0;
        doctorCount = 0;
    }

    public boolean addPatient(Patient p) {
        // Feature #6: if with jump statement 'return'
        if (p == null) {
            return false;
        }
        if (patientCount >= MAX_RECORDS) {
            System.out.println("Patient records full!");
            return false;
        }
        patients[patientCount] = p;
        patientCount++;
        return true;
    }

    public boolean addDoctor(Doctor d) {
        if (d == null) {
            return false;
        }
        if (doctorCount >= MAX_RECORDS) {
            return false;
        }
        doctors[doctorCount] = d;
        doctorCount++;
        return true;
    }

    /**
     * Feature #6 (loops): classic for-loop over the array of objects.
     * Feature #8 (formatted console output): uses printf.
     */
    public void listAllPatients() {
        System.out.println("\n--- Registered Patients (" + patientCount + ") ---");
        for (int i = 0; i < patientCount; i++) {
            System.out.printf("%d. %s%n", (i + 1), patients[i].toString());
        }
    }

    public void listAllDoctors() {
        System.out.println("\n--- Registered Doctors (" + doctorCount + ") ---");
        // Feature #6: enhanced for-loop (a different loop variant)
        int index = 1;
        for (Doctor d : doctors) {
            if (d == null) {
                continue; // jump statement: skip unused slots
            }
            System.out.printf("%d. %s%n", index, d.toString());
            index++;
        }
    }

    /**
     * Feature #13 (String methods): search patients by (partial, case
     * insensitive) name using contains()/toLowerCase(), a classic
     * "use of String class methods on user input" requirement.
     */
    public Patient searchPatientByName(String rawQuery) {
        if (rawQuery == null || rawQuery.trim().isEmpty()) {
            return null;
        }
        String query = rawQuery.trim().toLowerCase(Locale.ROOT); // String methods: trim, toLowerCase
        int i = 0;
        // Feature #6: while loop + break jump statement
        while (i < patientCount) {
            String candidateName = patients[i].getName().toLowerCase(Locale.ROOT);
            if (candidateName.contains(query)) {
                return patients[i]; // jump statement: return
            }
            i++;
        }
        return null;
    }

    /**
     * Feature #6 (do-while loop) + Feature #18 (interface reference):
     * walks every patient and totals their bills, accessed purely through
     * the Payable interface type, not the concrete Patient type.
     */
    public double totalHospitalRevenue() {
        double total = 0.0;
        if (patientCount == 0) {
            return total;
        }
        int i = 0;
        do {
            Payable billable = patients[i]; // interface reference (Feature #18)
            total += billable.calculateBill();
            i++;
        } while (i < patientCount);
        return total;
    }

    public int getPatientCount() {
        return patientCount;
    }

    public int getDoctorCount() {
        return doctorCount;
    }

    public Patient[] getPatients() {
        return patients;
    }

    public Doctor[] getDoctors() {
        return doctors;
    }

    /**
     * Feature #16 (dynamic binding): accepts Person references that may
     * actually be Patient or Doctor objects at runtime, and calls the
     * overridden displayRole()/toString() through the parent reference.
     */
    public void printRoster(Person[] roster) {
        System.out.println("\n--- Full Hospital Roster (Dynamic Binding Demo) ---");
        if (roster == null) {
            return;
        }
        for (Person p : roster) {
            if (p == null) {
                continue;
            }
            // p is declared as Person but calls the CHILD's overridden method
            System.out.printf("[%s] %s%n", p.displayRole(), p.toString());
        }
    }
}
