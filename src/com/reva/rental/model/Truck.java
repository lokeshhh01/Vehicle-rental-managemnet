package com.reva.rental.model;

/**
 * Class: Truck (Subclass of Vehicle)
 * 
 * Demonstrates Core OOP Concepts (Unit I & II):
 * 1. Inheritance: 'Truck extends Vehicle'.
 * 2. 'super' Keyword: Parent constructor invocation and display delegation.
 * 3. Polymorphism: Specialized tonnage-based pricing in calculateRentalCost(int days).
 * 4. Encapsulation: Commercial payload and axle specifications protected with getters/setters.
 * 
 * Student: AVULU LOKESH (SRN: R24SA007)
 * REVA University - B.Sc. (BSTCs) Semester V
 */
public class Truck extends Vehicle {

    // Truck-specific encapsulated fields
    private double cargoCapacityTons;
    private int numberOfAxles;
    private boolean hasCommercialPermit;

    /**
     * Overloaded Constructor (Convenience: defaults axles to 2 and permit to true):
     */
    public Truck(String vehicleId, String brand, String model, double baseDailyRate, double cargoCapacityTons) {
        this(vehicleId, brand, model, 2022, baseDailyRate, cargoCapacityTons, 2, true);
    }

    /**
     * Full Parameterized Constructor:
     * Chains to superclass constructor with 'super(...)'.
     */
    public Truck(String vehicleId, String brand, String model, int manufacturingYear,
                 double baseDailyRate, double cargoCapacityTons, int numberOfAxles, boolean hasCommercialPermit) {
        super(vehicleId, brand, model, manufacturingYear, baseDailyRate, VehicleType.TRUCK);
        this.cargoCapacityTons = (cargoCapacityTons > 0) ? cargoCapacityTons : 1.0;
        this.numberOfAxles = (numberOfAxles >= 2) ? numberOfAxles : 2;
        this.hasCommercialPermit = hasCommercialPermit;
    }

    // ==========================================
    // METHOD OVERRIDING (Runtime Polymorphism)
    // ==========================================

    /**
     * Overrides calculateRentalCost(int rentalDays).
     * Business Logic for Commercial Truck:
     * - Base cost = baseDailyRate * rentalDays
     * - Payload surcharge = cargoCapacityTons * ₹120 / day
     * - Commercial interstate transit permit surcharge = ₹300 flat
     */
    @Override
    public double calculateRentalCost(int rentalDays) {
        double total = this.baseDailyRate * rentalDays;

        // Payload tonnage factor
        total += (this.cargoCapacityTons * 120.0 * rentalDays);

        // Commercial transit permit fee
        if (this.hasCommercialPermit) {
            total += 300.0;
        }

        return total;
    }

    /**
     * Overrides displayDetails() using super.displayDetails().
     */
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.printf("  Cargo Limit  : %.1f Metric Tons%n", this.cargoCapacityTons);
        System.out.println("  Axle Count   : " + this.numberOfAxles + " Axles");
        System.out.println("  Permit Status: " + (this.hasCommercialPermit ? "All-India Commercial Permit (Active)" : "Local Goods Only"));
    }

    // ==========================================
    // GETTERS AND SETTERS
    // ==========================================

    public double getCargoCapacityTons() {
        return cargoCapacityTons;
    }

    public void setCargoCapacityTons(double cargoCapacityTons) {
        if (cargoCapacityTons > 0) {
            this.cargoCapacityTons = cargoCapacityTons;
        }
    }

    public int getNumberOfAxles() {
        return numberOfAxles;
    }

    public void setNumberOfAxles(int numberOfAxles) {
        if (numberOfAxles >= 2) {
            this.numberOfAxles = numberOfAxles;
        }
    }

    public boolean isHasCommercialPermit() {
        return hasCommercialPermit;
    }

    public void setHasCommercialPermit(boolean hasCommercialPermit) {
        this.hasCommercialPermit = hasCommercialPermit;
    }
}
