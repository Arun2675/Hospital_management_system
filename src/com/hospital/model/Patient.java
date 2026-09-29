package com.hospital.model;

/**
 * Feature #14 (Inheritance hierarchy): Patient extends Person.
 * Feature #18 (Interface): Patient implements Payable.
 */
public class Patient extends Person implements Payable {

    // ---- Feature #1: private fields (encapsulation) ----
    private String patientId;   // e.g. "P001"
    private Department department;
    private int numberOfDaysAdmitted;
    private double extraCharges;
    private boolean isEmergencyCase;

    // ---- Feature #11: static field + static counter ----
    private static int totalPatients = 0;
    private static final String ID_PREFIX = "P"; // final constant, local to this class

    // ---- Feature #9: Constructor overloading (default + parameterized) ----
    public Patient() {
        // Feature #15: 'super()' explicitly calls the parent no-arg constructor
        super();
        this.department = Department.GENERAL;
        this.numberOfDaysAdmitted = 1;
        this.extraCharges = 0.0;
        this.isEmergencyCase = false;
        totalPatients++;
        this.patientId = ID_PREFIX + String.format("%03d", totalPatients);
    }

    public Patient(String name, int age, char gender, String contactNumber,
                   Department department, int numberOfDaysAdmitted,
                   double extraCharges, boolean isEmergencyCase) {
        // Feature #15: super(...) calls the parameterized parent constructor
        super(name, age, gender, contactNumber);
        this.department = department == null ? Department.GENERAL : department;
        this.numberOfDaysAdmitted = normalizeDays(numberOfDaysAdmitted);
        this.extraCharges = normalizeCharge(extraCharges);
        this.isEmergencyCase = isEmergencyCase;
        totalPatients++;
        this.patientId = ID_PREFIX + String.format("%03d", totalPatients);
    }

    // ---- Feature #1: getters / setters ----
    public String getPatientId() {
        return patientId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department == null ? Department.GENERAL : department;
    }

    public int getNumberOfDaysAdmitted() {
        return numberOfDaysAdmitted;
    }

    public void setNumberOfDaysAdmitted(int numberOfDaysAdmitted) {
        this.numberOfDaysAdmitted = normalizeDays(numberOfDaysAdmitted);
    }

    public double getExtraCharges() {
        return extraCharges;
    }

    public void setExtraCharges(double extraCharges) {
        this.extraCharges = normalizeCharge(extraCharges);
    }

    public boolean isEmergencyCase() {
        return isEmergencyCase;
    }

    public void setEmergencyCase(boolean emergencyCase) {
        isEmergencyCase = emergencyCase;
    }

    public static int getTotalPatients() {
        return totalPatients;
    }

    // ---- Feature #10: Method overloading ----
    // Version 1: bill based only on days admitted
    public double calculateBill(int days) {
        double roomChargePerDay = 800.0;
        return roomChargePerDay * normalizeDays(days) + department.getBaseConsultationFee();
    }

    // Version 2 (overloaded): bill including extra charges and emergency surcharge
    public double calculateBill(int days, double extra, boolean emergency) {
        double roomChargePerDay = 800.0;
        double emergencySurcharge = emergency ? 1000.0 : 0.0;
        days = normalizeDays(days);
        extra = normalizeCharge(extra);
        // Feature #3: operator precedence relied upon deliberately.
        // Multiplication/division bind tighter than +/-, so this evaluates as:
        // (roomChargePerDay * days) + (department.getBaseConsultationFee()) + extra + emergencySurcharge
        double total = roomChargePerDay * days + department.getBaseConsultationFee() + extra + emergencySurcharge;
        return total;
    }

    private static int normalizeDays(int days) {
        return Math.max(days, 1);
    }

    private static double normalizeCharge(double charge) {
        return Double.isFinite(charge) ? Math.max(charge, 0.0) : 0.0;
    }

    // ---- Feature #18: implementation of the Payable interface method ----
    @Override
    public double calculateBill() {
        // Uses the overloaded 2-arg-style calculation with this patient's own state
        double subtotal = calculateBill(numberOfDaysAdmitted, extraCharges, isEmergencyCase);
        double withTax = subtotal + (subtotal * Payable.SERVICE_TAX_RATE);
        return withTax;
    }

    /**
     * Feature #4 (Type conversion / casting): the hospital rounds the final
     * bill DOWN to the nearest whole rupee for the printed receipt, so a
     * double is explicitly cast to an int.
     */
    public int getRoundedBillAmount() {
        double bill = calculateBill();
        int roundedBill = (int) bill; // explicit narrowing cast: double -> int
        return roundedBill;
    }

    // ---- Feature #16: method overriding (dynamic binding demonstrated in Main) ----
    @Override
    public String displayRole() {
        return "Patient";
    }

    /**
     * Feature #13 (String class methods): parses a raw symptom-entry string
     * typed by the user, e.g. "fever, cough , chest pain" using trim(),
     * split(), and compareTo()/equalsIgnoreCase().
     */
    public String[] parseSymptoms(String rawSymptomInput) {
        String cleaned = rawSymptomInput == null ? "" : rawSymptomInput.trim(); // String method: trim
        String[] symptoms = cleaned.split(",");                // String method: split
        // Feature #6: enhanced for-loop + continue (jump statement)
        for (int i = 0; i < symptoms.length; i++) {
            symptoms[i] = symptoms[i].trim();
            if (symptoms[i].isEmpty()) {
                continue; // jump statement: skip empty tokens
            }
        }
        // String method: compareTo used to flag urgent cases
        for (String symptom : symptoms) {
            if ("chest pain".compareToIgnoreCase(symptom) == 0) {
                this.isEmergencyCase = true;
                break;
            }
        }
        return symptoms;
    }

    // ---- Feature #19: Patient-specific override of toString()/equals() ----
    @Override
    public String toString() {
        // Feature #15: super.toString() -> calls parent class method explicitly
        return super.toString() + String.format(
                " | PatientID: %s | Dept: %s | Days: %d | Emergency: %b",
                patientId, department, numberOfDaysAdmitted, isEmergencyCase);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Patient)) return false;
        Patient other = (Patient) obj;
        return this.patientId.equals(other.patientId);
    }

    @Override
    public int hashCode() {
        return patientId.hashCode();
    }
}
