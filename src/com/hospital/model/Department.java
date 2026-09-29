package com.hospital.model;

/**
 * Feature #5 (Enum): Department enum used across the hospital system.
 * Each constant carries a base consultation fee, showing that enums
 * in Java can hold fields, constructors, and methods just like classes.
 */
public enum Department {
    CARDIOLOGY(1500.0),
    NEUROLOGY(1800.0),
    ORTHOPEDICS(1200.0),
    PEDIATRICS(900.0),
    GENERAL(500.0);

    // instance variable inside enum
    private final double baseConsultationFee;

    Department(double baseConsultationFee) {
        this.baseConsultationFee = baseConsultationFee;
    }

    public double getBaseConsultationFee() {
        return baseConsultationFee;
    }
}
