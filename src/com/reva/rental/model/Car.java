package com.reva.rental.model;

/**
 * Class: Car (Subclass of Vehicle)
 * 
 * Demonstrates Core OOP Concepts (Unit I & II):
 * 1. Inheritance: 'Car extends Vehicle' inherits all state and behavior of Vehicle.
 * 2. 'super' Keyword:
 *    - super(...) calls the parent class constructor to initialize common fields.
 *    - super.displayDetails() invokes the superclass method before adding Car-specific details.
 * 3. Method Overriding (Runtime Polymorphism):
 *    - Overrides calculateRentalCost(int days) with Car-specific business logic (AC charge, seating factor).
 * 4. Encapsulation: Private car-specific fields with public getters/setters.
 * 
 * Student: AVULU LOKESH (SRN: R24SA007)
 * REVA University - B.Sc. (BSTCs) Semester V
 */
public class Car extends Vehicle {

    // Car-specific encapsulated fields
    private int seatingCapacity;
    private String fuelType; // "Petrol", "Diesel", "Electric", "Hybrid"
    private boolean hasAirConditioning;

    /**
     * Overloaded Constructor (Convenience: defaults seating to 5 and AC to true):
     */
    public Car(String vehicleId, String brand, String model, double baseDailyRate, String fuelType) {
        this(vehicleId, brand, model, 2023, baseDailyRate, 5, fuelType, true);
    }

    /**
     * Full Parameterized Constructor:
     * Uses 'super(...)' to initialize inherited fields from Vehicle.
     */
    public Car(String vehicleId, String brand, String model, int manufacturingYear,
               double baseDailyRate, int seatingCapacity, String fuelType, boolean hasAirConditioning) {
        // Calling superclass (Vehicle) constructor using super keyword with VehicleType enum
        super(vehicleId, brand, model, manufacturingYear, baseDailyRate, VehicleType.CAR);
        this.seatingCapacity = seatingCapacity;
        this.fuelType = fuelType;
        this.hasAirConditioning = hasAirConditioning;
    }

    // ==========================================
    // METHOD OVERRIDING (Runtime Polymorphism)
    // ==========================================

    /**
     * Overrides abstract method from Vehicle.
     * Business Logic for Car:
     * - Base cost = baseDailyRate * rentalDays
     * - AC surcharge = ₹150 / day if AC enabled
     * - Large seating surcharge (7-seater or more) = ₹250 / day
     */
    @Override
    public double calculateRentalCost(int rentalDays) {
        double total = this.baseDailyRate * rentalDays;

        if (this.hasAirConditioning) {
            total += (150.0 * rentalDays); // ₹150 per day for Air Conditioning comfort
        }

        if (this.seatingCapacity > 5) {
            total += (250.0 * rentalDays); // Surcharge for 7+ seater family/SUV cars
        }

        return total;
    }

    /**
     * Overrides superclass displayDetails() method.
     * Uses super.displayDetails() to reuse parent printing logic.
     */
    @Override
    public void displayDetails() {
        super.displayDetails(); // Call parent Vehicle method
        System.out.println("  Seating      : " + this.seatingCapacity + " Passengers");
        System.out.println("  Fuel Type    : " + this.fuelType);
        System.out.println("  AC Equipped  : " + (this.hasAirConditioning ? "Yes (Rs. 150/day surcharge)" : "No"));
    }

    // ==========================================
    // GETTERS AND SETTERS
    // ==========================================

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        if (seatingCapacity > 0) {
            this.seatingCapacity = seatingCapacity;
        }
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public boolean isHasAirConditioning() {
        return hasAirConditioning;
    }

    public void setHasAirConditioning(boolean hasAirConditioning) {
        this.hasAirConditioning = hasAirConditioning;
    }
}
