package com.hospital.model;

/**
 * Feature #18 (Interface): Implemented by any entity in the hospital
 * that generates a bill (currently Patient). Accessed via an interface
 * reference in HospitalService (Payable p = patient;).
 */
public interface Payable {

    // interface constant (implicitly public static final)
    double SERVICE_TAX_RATE = 0.05; // 5% service tax

    double calculateBill();

    // default method: every Payable gets a formatted receipt line for free
    default String generateReceiptLine() {
        return String.format("Amount Payable (incl. tax): Rs. %.2f", calculateBill());
    }
}
