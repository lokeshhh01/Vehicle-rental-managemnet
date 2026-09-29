package com.reva.rental.model;

/**
 * Class: Motorcycle (Subclass of Vehicle)
 * 
 * Demonstrates Core OOP Concepts (Unit I & II):
 * 1. Inheritance: 'Motorcycle extends Vehicle'.
 * 2. 'super' Keyword: Constructor forwarding and superclass method invocation.
 * 3. Polymorphism: Specialized implementation of calculateRentalCost(int days).
 * 4. Encapsulation: Private bike-specific attributes with getters/setters.
 * 
 * Student: AVULU LOKESH (SRN: R24SA007)
 * REVA University - B.Sc. (BSTCs) Semester V
 */
public class Motorcycle extends Vehicle {

    // Motorcycle-specific fields
    private int engineCapacityCC;
    private String bikeCategory; // "Commuter", "Sports", "Cruiser"
    private boolean includesHelmetAndGear;

    /**
     * Overloaded Constructor (Convenience: defaults category to Commuter):
     */
    public Motorcycle(String vehicleId, String brand, String model, double baseDailyRate, int engineCapacityCC) {
        this(vehicleId, brand, model, 2023, baseDailyRate, engineCapacityCC, "Commuter", true);
    }

    /**
     * Full Parameterized Constructor:
     * Chains to superclass constructor with 'super(...)'.
     */
    public Motorcycle(String vehicleId, String brand, String model, int manufacturingYear,
                      double baseDailyRate, int engineCapacityCC, String bikeCategory, boolean includesHelmetAndGear) {
        super(vehicleId, brand, model, manufacturingYear, baseDailyRate, VehicleType.MOTORCYCLE);
        this.engineCapacityCC = engineCapacityCC;
        this.bikeCategory = bikeCategory;
        this.includesHelmetAndGear = includesHelmetAndGear;
    }

    // ==========================================
    // METHOD OVERRIDING (Runtime Polymorphism)
    // ==========================================

    /**
     * Overrides calculateRentalCost(int rentalDays).
     * Business Logic for Motorcycle:
     * - Base cost = baseDailyRate * rentalDays
     * - Premium engine displacement surcharge (> 350cc) = ₹180 / day
     * - Riding safety helmet/gear kit = ₹50 / day
     */
    @Override
    public double calculateRentalCost(int rentalDays) {
        double total = this.baseDailyRate * rentalDays;

        if (this.engineCapacityCC > 350) {
            total += (180.0 * rentalDays); // Heavy bike / Sports displacement surcharge
        }

        if (this.includesHelmetAndGear) {
            total += (50.0 * rentalDays); // Certified helmet and safety kit
        }

        return total;
    }

    /**
     * Overrides displayDetails() using super.displayDetails().
     */
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("  Displacement : " + this.engineCapacityCC + " cc");
        System.out.println("  Category     : " + this.bikeCategory);
        System.out.println("  Helmet & Kit : " + (this.includesHelmetAndGear ? "Provided (Rs. 50/day fee included)" : "Self-arranged"));
    }

    // ==========================================
    // GETTERS AND SETTERS
    // ==========================================

    public int getEngineCapacityCC() {
        return engineCapacityCC;
    }

    public void setEngineCapacityCC(int engineCapacityCC) {
        if (engineCapacityCC > 50) {
            this.engineCapacityCC = engineCapacityCC;
        }
    }

    public String getBikeCategory() {
        return bikeCategory;
    }

    public void setBikeCategory(String bikeCategory) {
        this.bikeCategory = bikeCategory;
    }

    public boolean isIncludesHelmetAndGear() {
        return includesHelmetAndGear;
    }

    public void setIncludesHelmetAndGear(boolean includesHelmetAndGear) {
        this.includesHelmetAndGear = includesHelmetAndGear;
    }
}
