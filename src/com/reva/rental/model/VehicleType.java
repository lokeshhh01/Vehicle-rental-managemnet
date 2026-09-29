package com.reva.rental.model;

/**
 * Enum: VehicleType
 * 
 * Demonstrates Java Enum (Unit I & II):
 * Defines a type-safe enumeration representing supported fleet vehicle categories.
 * 
 * Student: AVULU LOKESH (SRN: R24SA007)
 * REVA University - B.Sc. (BSTCs) Semester V
 */
public enum VehicleType {
    CAR("Car"),
    MOTORCYCLE("Motorcycle"),
    TRUCK("Truck");

    private final String displayName;

    VehicleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
